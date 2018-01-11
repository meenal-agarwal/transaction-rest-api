package com.transaction.task.controller;

import com.transaction.task.dto.Sum;
import com.transaction.task.exception.TransactionEntryNotFoundException;
import com.transaction.task.exception.TransactionTypeNotFoundException;
import com.transaction.task.model.Transaction;
import com.transaction.task.model.TransactionData;
import com.transaction.task.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    private final Transaction transaction10 = new Transaction(10L, 5000.0, "cars", Optional.empty());

    private final Transaction transaction11 = new Transaction(11L, 10000.0, "shopping", Optional.of(10l));

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService transactionService;

    @Mock
    Transaction transaction;


    // Test for Get Transaction.
    @Test
    public void testTransactionWithoutParentId() throws Exception {
        when(transactionService.getTransaction(10L))
                .thenReturn(transaction10);

        this.mvc.perform(get("/transaction/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"amount\":5000,\"type\":\"cars\"}"));
    }

    @Test
    public void testTransactionWithParentId() throws Exception {
        given(transactionService.getTransaction(11L))
                .willReturn(transaction11);

        this.mvc.perform(get("/transaction/11")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"amount\":10000,\"type\":\"shopping\",\"parent_id\":10}", true));
    }

    @Test
    public void testNoTransactionFound() throws Exception {
        given(transactionService.getTransaction(15L))
                .willThrow(new TransactionEntryNotFoundException("Transaction is not available"));

        this.mvc.perform(get("/transaction/15")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    // Get Transaction with Type.
    @Test
    public void GET_types_cars() throws Exception {
        when(transactionService.getTransactionByType("cars"))
                .thenReturn(new ArrayList<>(Arrays.asList(10l, 20l)));

        this.mvc.perform(get("/types/cars"))
                .andExpect(status().isOk())
                .andExpect(content().json("[10,20]", true));
    }

    @Test
    public void GET_types_shopping() throws Exception {
        given(transactionService.getTransactionByType("shopping"))
                .willReturn(new ArrayList<>(Arrays.asList(11l)));

        this.mvc.perform(get("/types/shopping")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[11]", true));
    }

    @Test
    public void GET_types_exception() throws Exception {
        given(transactionService.getTransactionByType("grocery"))
                .willThrow(new TransactionTypeNotFoundException("Type is not available"));

        this.mvc.perform(get("/types/grocery"))
                .andExpect(status().isNotFound());
    }



   /* @Test
    public void PUT_transaction_10() throws Exception {
        Mockito.doThrow(new Exception()).doNothing().when(transactionService.add).methodName();
        when(transactionService.addTransaction(transaction)).thenReturn(null);

        this.mvc.perform(put("/transaction/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"amount\":5000,\"type\":\"cars\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"ok\"}", true));
    }*/

    /*
    @Test
    public void PUT_transaction_11() throws Exception {
        given(transactionService.save(11L, 10000.0, "shopping", 10L))
                .willReturn(transaction11);

        this.mvc.perform(put("/transactionservice/transaction/11")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"amount\":10000,\"type\":\"shopping\",\"parent_id\":10 }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"ok\"}", true));
    }



*/


    // Test for SUM
    @Test
    public void GET_sum_10() throws Exception {
        given(transactionService.getTransactionSum(10L))
                .willReturn(new Sum(15000.0));

        this.mvc.perform(get("/sum/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"sum\":15000.0}"));
    }

    @Test
    public void GET_sum_11() throws Exception {
        given(transactionService.getTransactionSum(11L))
                .willReturn(new Sum(10000.0));

        this.mvc.perform(get("/sum/11")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"sum\":10000.0}"));
    }


    @Test
    public void GET_sum_15() throws Exception {
        given(transactionService.getTransactionSum(15L))
                .willThrow(new TransactionEntryNotFoundException("Transcation is not available"));

        this.mvc.perform(get("/sum/15")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}