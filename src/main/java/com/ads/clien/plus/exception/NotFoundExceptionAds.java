package com.ads.clien.plus.exception;

public class NotFoundExceptionAds extends RuntimeException {

    public NotFoundExceptionAds(String message) {
        super(message);
    }

    public NotFoundExceptionAds(String message, Throwable cause) {
        super(message, cause);
    }
}
