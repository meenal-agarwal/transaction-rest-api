package com.transaction.task.exception;


public class TransactionTypeNotFoundException extends RuntimeException {

    public TransactionTypeNotFoundException(String message){
        super(message);
    }
}
