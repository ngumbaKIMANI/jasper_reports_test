package com.james.jasperreportstest.service;

import com.james.jasperreportstest.entity.Employee;
import com.james.jasperreportstest.repository.EmployeeRepository;
import com.james.jasperreportstest.service.exceptions.ReportExportException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportsServiceImpl implements ReportService {
    private final EmployeeRepository employeeRepository;
    private final ReportExporter reportExporter;

    public ReportsServiceImpl(EmployeeRepository employeeRepository, ReportExporter reportExporter) {
        this.employeeRepository = employeeRepository;
        this.reportExporter = reportExporter;
    }

    public String exportReport(String reportFormat) {
        try {
            //fetch data from database
            List<Employee> employeeList = employeeRepository.findAll();

            //generate report
            return reportExporter.export(employeeList, reportFormat);
        } catch (ReportExportException e) {
             e.printStackTrace();
            // Return the exception message to the user
            return e.getMessage();
        }
    }
}
