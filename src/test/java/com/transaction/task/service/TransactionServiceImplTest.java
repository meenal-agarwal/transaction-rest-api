package com.transaction.task.service;

import com.transaction.task.exception.TransactionEntryNotFoundException;
import com.transaction.task.model.Transaction;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceImplTest {

    private final Transaction transaction10 = new Transaction(10L, 5000.0, "cars", Optional.empty());

    private final Transaction transaction11 = new Transaction(11L, 10000.0, "shopping", Optional.of(10l));

    private final Transaction transaction12 = new Transaction(12L, 2000.0, "shopping", Optional.of(11l));


    @Resource
    private  HashMap<Long,Transaction> transactionMap;

    @Autowired
    private TransactionService transactionService;




    @Before
    public void setup() {
        transactionMap.put(10L, transaction10);
        transactionMap.put(11L, transaction11);
        transactionMap.put(12L, transaction12);
    }

    @Test
    public void find10() throws Exception {
        assertEquals(transactionService.getTransaction(10L),(transaction10));
    }

    /*
    @Test
    public void find11() throws Exception {
        assertEquals(transactionService.getTransaction(11L),(transaction11));
    }

    @Test (expected = TransactionEntryNotFoundException.class)
    public void find15() throws Exception {
        transactionService.getTransaction(15L);
    }

//    @Test
//    public void save13() throws Exception {
//        Assertions.assertThat(transactionService.addTransaction(13L, 1000.0, "test", Optional.of(10L)))
//                .isNotNull()
//                .hasFieldOrPropertyWithValue("transactionId", 13L)
//                .hasFieldOrPropertyWithValue("amount", 1000.0)
//                .hasFieldOrPropertyWithValue("type", "test")
//                .hasFieldOrPropertyWithValue("parentId", 10L);
//    }
//
//
//    @Test (expected = TransactionAlreadyFoundException.class)
//    public void testTransactionAlreadyAvailble() throws Exception {
//        transactionService.addTransaction(10L,300.0, "Toys", Optional.empty());
//    }


    @Test
    public void findByTypeCars() throws Exception {
        Assertions.assertThat(transactionService.getTransactionByType("cars"))
                .isNotNull()
                .hasSize(1)
                .contains(10L);
    }

    @Test
    public void findByTypeShopping() throws Exception {
        Assertions.assertThat(transactionService.getTransactionByType("shopping"))
                .isNotNull()
                .hasSize(2)
                .containsExactly(11L,12L);
    }

    @Test (expected = TransactionEntryNotFoundException.class)
    public void findByTypeNotFound() throws Exception {
        transactionService.getTransactionByType("Grocery");
    }

    @Test
    public void sumAmountsFrom10() throws Exception {
        Assertions.assertThat(transactionService.getTransactionSum(10L))
                .isEqualTo(15000.0);
    }

    @Test
    public void sumAmountsFrom11() throws Exception {
        Assertions.assertThat(transactionService.getTransactionSum(11L))
                .isEqualTo(12000.0);
    }

    @Test (expected = TransactionEntryNotFoundException.class)
    public void sumAmountsForTransactionNotFound() throws Exception {
        transactionService.getTransactionSum(15L);
    }

    @Test
    public void testRequiredFields() throws Exception {
        Assertions.assertThat(transactionService.getTransaction(10L))
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept("parentId");
    }
*/
}