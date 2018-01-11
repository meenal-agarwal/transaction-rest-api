package com.transaction.task.service;

import com.transaction.task.dto.Sum;
import com.transaction.task.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction getTransaction(Long id);

    void addTransaction(Transaction transaction);

    List<Long> getTransactionByType(String type);

    Sum getTransactionSum(Long id);
}
