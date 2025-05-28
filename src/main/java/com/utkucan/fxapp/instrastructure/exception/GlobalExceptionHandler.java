package com.utkucan.fxapp.instrastructure.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.utkucan.fxapp.adapter.response.ApiResponse;
import com.utkucan.fxapp.adapter.response.FieldValidationError;
import com.utkucan.fxapp.common.exception.BaseException;
import com.utkucan.fxapp.common.exception.ExceptionCode;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Object>> handleBaseException(BaseException ex) {
        ExceptionCode code = ex.getExceptionCode();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(code.code(), code.message(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        ExceptionCode code = ExceptionCode.VALIDATION_EXCEPTION;
        List<FieldValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldValidationError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();


        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(code.code(), code.message(), errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        ExceptionCode code = ExceptionCode.VALIDATION_EXCEPTION;

        List<FieldValidationError> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> {
                    String field = violation.getPropertyPath().toString();
                    if (field.contains(".")) {
                        field = field.substring(field.lastIndexOf('.') + 1);
                    }
                    return new FieldValidationError(field, violation.getMessage());
                })
                .toList();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(code.code(), code.message(), errors));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMediaTypeNotAcceptableException.class,
            HttpMediaTypeNotSupportedException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleClientErrors(Exception ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("Invalid request payload", ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception ex) {
        ExceptionCode code = ExceptionCode.UNKNOWN_EXCEPTION;

        String traceId = UUID.randomUUID().toString().substring(0, 8);
        log.error("Unhandled error [{}]: {}", traceId, ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(code.code(), code.message(), null));
    }
}
