package com.transaction.task.service;


import com.transaction.task.dto.Sum;
import com.transaction.task.exception.TransactionAlreadyFoundException;
import com.transaction.task.exception.TransactionEntryNotFoundException;
import com.transaction.task.exception.TransactionTypeNotFoundException;
import com.transaction.task.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {


    private final HashMap<Long,Transaction> transactionMap;
    private  Map<Long, Set<Long>> parentChildMap = new HashMap<>() ;

    private  Set<Long> transactionIdSet = new HashSet<>();



    @Autowired
    public TransactionServiceImpl(HashMap<Long,Transaction> transactionMap) {
        this.transactionMap = transactionMap;
    }

    @Override
    public Transaction getTransaction(Long id) {
        if (transactionMap.containsKey(id))
            return transactionMap.get(id);
        else
            throw new TransactionEntryNotFoundException("Transcation is not available");
    }

    @Override
    public void addTransaction(Transaction transaction) {
        if (transactionMap.containsKey(transaction.getTransactionId()))
            throw new TransactionAlreadyFoundException("Transaction is already available");

        transactionMap.put(transaction.getTransactionId(),transaction);
        transactionIdSet.add(transaction.getTransactionId());

        //Storing child-parent relation.
        if (transaction.getParentId().isPresent()) {
            Set<Long> childTransactionIds = new HashSet<>();
            if (parentChildMap.containsKey(transaction.getParentId().get())) {
                childTransactionIds = parentChildMap.get(transaction.getParentId().get());
            }
            childTransactionIds.add(transaction.getTransactionId());
            parentChildMap.put(transaction.getParentId().get(), childTransactionIds);
        }
    }

    @Override
    public List<Long> getTransactionByType(String type) {
        List<Long> lst = new ArrayList<>();
        transactionMap.forEach((k,v) -> {
            if(v.getType().equals(type))
                lst.add(k);
        });

        if (lst.isEmpty()) {
            throw new TransactionTypeNotFoundException("Type is not available");
        }
        return lst;
    }

    @Override
    public Sum getTransactionSum(Long id) {
        double sum = 0;
        if(!transactionIdSet.contains(id)){
            throw new TransactionEntryNotFoundException("Transcation is not available");
        }

        Queue<Long> queue=new LinkedList<>();
        queue.add(id);
        while(!queue.isEmpty()){
            Long transactionId = queue.poll();
            sum = sum + transactionMap.get(transactionId).getAmount();
            if(parentChildMap.containsKey(transactionId))
                queue.addAll(parentChildMap.get(transactionId));
        }
        return new Sum(sum);
    }
}
