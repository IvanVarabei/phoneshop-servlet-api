package com.es.phoneshop.model.exception;

public class OutOfStockException extends Exception{
    private final int availableAmount;

    public OutOfStockException(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public OutOfStockException(String message, int availableAmount) {
        super(message);
        this.availableAmount = availableAmount;
    }

    public OutOfStockException(String message, Throwable cause, int availableAmount) {
        super(message, cause);
        this.availableAmount = availableAmount;
    }

    public OutOfStockException(Throwable cause, int availableAmount) {
        super(cause);
        this.availableAmount = availableAmount;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }
}
