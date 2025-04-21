package com.ads.clien.plus.exception;

public class IntegrationException extends RuntimeException {

    public IntegrationException(String message) {
        super(message);
    }
    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
