package com.utkucan.fxapp.adapter.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(description = "Standard API response wrapper that includes status, message, code, and data.")
public class RestResponse<T> {
    @Schema(description = "Indicates whether the request was successful or not", example = "true")
    private Boolean success;

    @Schema(description = "Error or success message", example = "Conversion completed successfully")
    private String message;

    @Schema(description = "Optional error code for failed operations", example = "FX001")
    private String code;

    @Schema(description = "The actual response data")
    private T data;

    @Schema(description = "Validation error details, if any")
    private List<FieldValidationError> validationErrors;

    private RestResponse(boolean success, String message, String code, T data, List<FieldValidationError> validationErrors) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
        this.validationErrors = validationErrors;
    }

    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<>(true, null, null, data, null);
    }

    public static <T> RestResponse<T> error(String code, String message, List<FieldValidationError> validationErrors) {
        return new RestResponse<>(false, message, code, null, validationErrors);
    }
}
