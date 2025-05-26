package com.utkucan.fxapp.adapter.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private String code;
    private T data;
    private List<FieldValidationError> validationErrors;

    private ApiResponse(boolean success, String message, String code, T data, List<FieldValidationError> validationErrors) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
        this.validationErrors = validationErrors;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, null, null, data, null);
    }

    public static <T> ApiResponse<T> error(String code, String message, List<FieldValidationError> validationErrors) {
        return new ApiResponse<>(false, message, code, null, validationErrors);
    }
}
