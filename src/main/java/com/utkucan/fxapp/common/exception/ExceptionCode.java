package com.utkucan.fxapp.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standardized exception codes used across the API")
public enum ExceptionCode {

    @Schema(description = "Invalid or unsupported currency code provided by the client")
    INVALID_CURRENCY_EXCEPTION("FX001", "Invalid currency code."),

    @Schema(description = "Exchange rate provider is not responding or returned an unexpected response")
    EXTERNAL_API_FAILURE_EXCEPTION("FX002", "Exchange rate provider is not responding."),

    @Schema(description = "Failed to read the uploaded file. May be due to format or access issues")
    FILE_READ_EXCEPTION("FX003", "Failed to read the uploaded file. Please make sure it is a valid and accessible format."),

    @Schema(description = "Request failed validation checks. One or more fields contain invalid data")
    VALIDATION_EXCEPTION("FX400", "Validation failed."),

    @Schema(description = "unknown server error")
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
