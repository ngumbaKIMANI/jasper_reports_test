package com.james.jasperreportstest.controller;

import com.james.jasperreportstest.entity.Employee;
import com.james.jasperreportstest.service.EmployeeService;
import com.james.jasperreportstest.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ReportService reportsService;

    @GetMapping("/")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) {
        return reportsService.exportReport(format);
    }
}
