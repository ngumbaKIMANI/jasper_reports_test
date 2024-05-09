package com.james.jasperreportstest.service;

import com.james.jasperreportstest.entity.Employee;
import com.james.jasperreportstest.service.exceptions.ReportExportException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Component;

import java.nio.file.FileSystems;
import java.util.List;

@Component
public class ReportExporter {
    private final String homePath = System.getProperty("user.home");
    private final String separator = FileSystems.getDefault().getSeparator();
    private final String filePath = homePath + separator + "reports" + separator;
    private final ReportCompiler reportCompiler;

    public ReportExporter(ReportCompiler reportCompiler) {
        this.reportCompiler = reportCompiler;
    }

    public String export(List<Employee> employeeList, String reportFormat) throws ReportExportException {
        JasperPrint jasperPrint = reportCompiler.compileReport(employeeList);

        return switch (reportFormat.toLowerCase()) {
            case "pdf" -> exportAsPdf(jasperPrint);
            case "xlsx" -> exportAsXlsx(jasperPrint);
            case "docx" -> exportAsDocx(jasperPrint);
            default -> throw new ReportExportException("Invalid report format: " + reportFormat);
        };
    }

    private String exportAsPdf(JasperPrint jasperPrint) throws ReportExportException {
        try {
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath + "EmployeeReport.pdf");
            return "PDF report generated in " + filePath + "EmployeeReport.pdf";
        } catch (JRException e) {
            throw new ReportExportException("Failed to export report as PDF", e);
        }
    }

    private String exportAsXlsx(JasperPrint jasperPrint) throws ReportExportException {
        try {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath + "EmployeeReport.xlsx"));
            exporter.exportReport();
            return "XLSX report generated in " + filePath + "EmployeeReport.xlsx";
        } catch (JRException e) {
            throw new ReportExportException("Failed to export report as XLSX", e);
        }
    }

    private String exportAsDocx(JasperPrint jasperPrint) throws ReportExportException {
        try {
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath + "EmployeeReport.docx"));
            exporter.exportReport();
            return "DOCX report generated in " + filePath + "EmployeeReport.docx";
        } catch (JRException e) {
            throw new ReportExportException("Failed to export report as DOCX", e);
        }
    }
}
