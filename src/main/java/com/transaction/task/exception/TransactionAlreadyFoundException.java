package com.transaction.task.exception;

public class TransactionAlreadyFoundException extends RuntimeException {

    public TransactionAlreadyFoundException(String message) {
        super(message);
    }
}
