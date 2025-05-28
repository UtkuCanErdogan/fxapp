package com.utkucan.fxapp.common.exception;

public class InvalidCurrencyException extends BaseException {
  private final ExceptionCode exceptionCode;

  public InvalidCurrencyException(ExceptionCode exceptionCode) {
    super(exceptionCode);
    this.exceptionCode = exceptionCode;
  }

  public ExceptionCode getExceptionCode() {
    return exceptionCode;
  }
}
