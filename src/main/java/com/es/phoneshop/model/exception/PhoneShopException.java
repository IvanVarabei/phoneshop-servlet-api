package com.es.phoneshop.model.exception;

public class PhoneShopException extends Exception{
    public PhoneShopException() {
    }

    public PhoneShopException(String message) {
        super(message);
    }

    public PhoneShopException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneShopException(Throwable cause) {
        super(cause);
    }
}
