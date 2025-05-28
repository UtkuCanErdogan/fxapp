package com.utkucan.fxapp.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class CsvUploadRequest {
    @NotNull
    @Size(max = 5, message = "You can upload up to 5 files.")
    private MultipartFile[] files;
}
