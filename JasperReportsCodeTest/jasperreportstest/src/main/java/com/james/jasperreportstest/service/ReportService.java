package com.james.jasperreportstest.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface ReportService {
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException;
}
