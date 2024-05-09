package com.james.jasperreportstest.service.exceptions;

public class ReportExportException extends Exception {
    public ReportExportException(String message) {
        super(message);
    }

    public ReportExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
