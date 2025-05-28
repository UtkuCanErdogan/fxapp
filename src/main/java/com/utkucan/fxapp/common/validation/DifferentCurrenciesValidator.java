package com.utkucan.fxapp.common.validation;

import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DifferentCurrenciesValidator implements ConstraintValidator<DifferentCurrencies, ExchangeRequest> {

    @Override
    public boolean isValid(ExchangeRequest value, ConstraintValidatorContext context) {
        if (value.getFrom() != null && value.getTarget() != null && value.getFrom().equals(value.getTarget())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Source and target currencies must be different")
                    .addPropertyNode("from")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
