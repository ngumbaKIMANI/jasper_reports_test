package com.james.jasperreportstest.service;

import com.james.jasperreportstest.entity.Employee;
import com.james.jasperreportstest.service.exceptions.ReportExportException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReportCompiler {
    public JasperPrint compileReport(List<Employee> employeeList) throws ReportExportException {
        try {
            // Load template
            File file = ResourceUtils.getFile("classpath:report_templates/EmployeeReport.jrxml");

            // Compile template
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            // Map data to template
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeList);

            //provide required parameters
            Map<String, Object> parameters = new HashMap<>();

            // Fill report
            return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        } catch (FileNotFoundException | JRException e) {
            throw new ReportExportException("Failed to compile report", e);
        }
    }
}

