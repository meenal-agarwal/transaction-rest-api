package com.transaction.task.controller;

import com.transaction.task.dto.Sum;
import com.transaction.task.model.HTTPResponse;
import com.transaction.task.model.Transaction;
import com.transaction.task.model.TransactionData;
import com.transaction.task.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/transaction/{transaction_id}", method = RequestMethod.PUT)
    @ResponseStatus(value= HttpStatus.OK)
    @ResponseBody
    public HTTPResponse addTransaction(@PathVariable("transaction_id") Long transactionId,
                                       @RequestBody @Valid TransactionData transaction) {

        transactionService.addTransaction(new Transaction(transactionId,transaction.getAmount(),transaction.getType(),
                Optional.ofNullable(transaction.getParent_id())));

        return new HTTPResponse(HttpStatus.OK.getReasonPhrase());
    }



    @RequestMapping(value = "/transaction/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TransactionData getTransaction(@PathVariable Long id) {
        return TransactionData.of(transactionService.getTransaction(id));
    }

    @GetMapping(value = "/types/{type}")
    @ResponseBody
    public List<Long> getTypeTransaction(@PathVariable String type) {
        return transactionService.getTransactionByType(type);
    }

    @GetMapping(value = "/sum/{id}")
    @ResponseBody
    public Sum getSumTransaction(@PathVariable Long id) {
        return transactionService.getTransactionSum(id);
    }
}
