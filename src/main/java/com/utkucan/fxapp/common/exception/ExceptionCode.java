package com.utkucan.fxapp.common.exception;

public enum ExceptionCode {
    INVALID_CURRENCY_EXCEPTION("FX001", "Invalid currency code."),
    EXTERNAL_API_FAILURE_EXCEPTION("FX002", "Exchange rate provider is not responding."),
    FILE_READ_EXCEPTION("FX003", "Failed to read the uploaded file. Please make sure it is a valid and accessible format."),
    VALIDATION_EXCEPTION("FX400", "Validation failed."),
    UNKNOWN_EXCEPTION("FX999", "Something went wrong. Please try again later.");

    private final String code;
    private final String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() { return code; }
    public String message() { return message; }
}
