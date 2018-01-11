package com.transaction.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Optional;

@Data
public class Transaction {

    private Long transactionId;

    private Double amount;

    private String type;

    private Optional<Long> parentId;

    public Transaction(Long transactionId, Double amount, String type, Optional<Long> parentId) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }
}
