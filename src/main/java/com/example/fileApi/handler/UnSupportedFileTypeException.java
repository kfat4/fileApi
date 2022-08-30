package com.example.fileApi.handler;

public class UnSupportedFileTypeException extends RuntimeException {

    public UnSupportedFileTypeException(String message) {
        super(message);
    }

    public UnSupportedFileTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
