package com.ads.clien.plus.exception;

public class BusinessexceptionAds extends RuntimeException {

    public BusinessexceptionAds(String message) {
        super(message);
    }

    public BusinessexceptionAds(String message, Throwable cause) {
        super(message, cause);
    }
}
