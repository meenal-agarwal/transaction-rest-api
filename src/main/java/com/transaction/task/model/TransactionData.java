package com.transaction.task.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TransactionData {

    @NotNull
    private Double amount;

    @NotNull
    private String type;

    private Long parent_id;

    public static TransactionData of (Transaction transaction){

        TransactionData transactionData = new TransactionData();
        transactionData.setAmount(transaction.getAmount());
        transactionData.setType(transaction.getType());
        transactionData.setParent_id(transaction.getParentId().orElse(null));
        return transactionData;
    }


}
