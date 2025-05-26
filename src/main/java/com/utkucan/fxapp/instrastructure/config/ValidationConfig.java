package com.utkucan.fxapp.instrastructure.config;

import com.utkucan.fxapp.common.utils.ValidationUtil;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
    public ValidationConfig(Validator validator) {
        ValidationUtil.setValidator(validator);
    }
}
