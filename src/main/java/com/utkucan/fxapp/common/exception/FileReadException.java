package com.utkucan.fxapp.common.exception;

public class FileReadException extends BaseException {
    private final ExceptionCode exceptionCode;

    public FileReadException(ExceptionCode exceptionCode) {
        super(exceptionCode);
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
