package com.ads.clien.plus.exception;

public class BadReqequestExceptionAds extends RuntimeException {

    public BadReqequestExceptionAds(String message) {
        super(message);
    }

    public BadReqequestExceptionAds(String message, Throwable cause) {
        super(message, cause);
    }
}
