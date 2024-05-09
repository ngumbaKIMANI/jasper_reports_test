package com.james.jasperreportstest.service;

import com.james.jasperreportstest.entity.Employee;
import com.james.jasperreportstest.repository.EmployeeRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportsServiceImpl implements ReportService {
    private final EmployeeRepository employeeRepository;
    String homePath = System.getProperty("user.home");
    String separator = FileSystems.getDefault().getSeparator();
    String filePath = homePath + separator + "reports" + separator;
    public ReportsServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        List<Employee> employeeList = employeeRepository.findAll();

        //load template
        File file = ResourceUtils.getFile("classpath:report_templates/EmployeeReport.jrxml");

        //compile template
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        //map data to template
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeList);

        Map<String, Object> parameters = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        //pdf
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath + "EmployeeReport.pdf");
        } else
            //excel
            if (reportFormat.equalsIgnoreCase("xlsx")) {
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath + "EmployeeReport.xlsx"));
                exporter.exportReport();
            } else

                //word
                if (reportFormat.equalsIgnoreCase("docx")) {
                    JRDocxExporter exporter = new JRDocxExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath + "EmployeeReport.docx"));
                    exporter.exportReport();
                } else {
                    return "\"" + reportFormat + "\"" + " is not a correct format, " +
                            "choose \"pdf\", \"docx\" or \"xlsx\" " +
                            "report " +
                            "formats ";
                }
        return "Report generated in " + filePath;
    }
}
