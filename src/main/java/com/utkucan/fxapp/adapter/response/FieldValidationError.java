package com.utkucan.fxapp.adapter.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Details of a validation error for a specific field")
public class FieldValidationError {
    @Schema(description = "The name of the field with the validation error", example = "amount")
    private String field;

    @Schema(description = "The validation error message", example = "Amount must be at least 100")
    private String message;

    public FieldValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
