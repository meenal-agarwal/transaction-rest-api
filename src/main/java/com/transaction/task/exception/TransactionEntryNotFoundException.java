package com.transaction.task.exception;

public class TransactionEntryNotFoundException extends RuntimeException {

    public TransactionEntryNotFoundException(String message) {
        super(message);
    }
}
