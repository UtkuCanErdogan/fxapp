package com.utkucan.fxapp.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DifferentCurrenciesValidator.class)
public @interface DifferentCurrencies {
    String message() default "Source and target currencies must be different";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}