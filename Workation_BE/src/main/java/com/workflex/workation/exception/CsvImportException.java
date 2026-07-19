package com.workflex.workation.exception;

public class CsvImportException extends RuntimeException {

    public CsvImportException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvImportException(String message) {
        super(message);
    }
}
