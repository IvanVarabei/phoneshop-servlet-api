package com.es.phoneshop.controller.value;

public class ErrorInfo {
    public static final String NOT_FOUND = "Product with code '%s' not found.";
    public static final String NOT_ENOUGH_STOCK = "Not enough stock. Available:%s";
    public static final String NOT_NUMBER = "Not a number";
    public static final int PAGE_NOT_FOUND_CODE = 404;

    private ErrorInfo() {
    }
}
