package com.utkucan.fxapp.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class Pagination {

    @Min(1)
    @Max(100)
    @Schema(description = "Page limit (max 100)", example = "10")
    private Integer limit;

    @Min(0)
    @Schema(description = "Skip offset", example = "0")
    private Integer skip;

    @Schema(description = "Sort direction", example = "DESC", allowableValues = {"ASC", "DESC"})
    private String sortBy;
}
