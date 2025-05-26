package com.utkucan.fxapp.common.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.Setter;

import java.util.Set;
import java.util.function.Supplier;

public class ValidationUtil {
    @Setter
    private static Validator validator;

    private ValidationUtil() {}

    public static <T, R> R validateAndRun(T input, Supplier<R> function) {
        Set<ConstraintViolation<T>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return function.get();
    }
}
