package com.db.asset.management.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.db.asset.management.dao.Account;
import com.db.asset.management.dao.AmountTransfer;
import com.db.asset.management.exception.AccountNotFoundException;
import com.db.asset.management.exception.TransferBetweenSameAccountException;

@ExtendWith(MockitoExtension.class)
public class TestTransferValidator {

    @InjectMocks 
    private TransferValidator transferValidator;
	
	@Test
    public void validate_should_throwException_when_accountFromNotFound() throws Exception {        
        try {
            transferValidator.validate(null, new Account("Id-2"));
            fail("Account with Id-1 should not be found");
        } catch (AccountNotFoundException ace) {
            assertThat(ace.getMessage()).isEqualTo("Please check account details.");
        }
    }

    @Test
    public void validate_should_throwException_when_accountToNotFound() throws Exception {
        final Account accountFrom = new Account("Id-1");
        try {
            transferValidator.validate(accountFrom, null);
            fail("Account with Id-5342 should not be found");
        } catch (AccountNotFoundException ace) {
            assertThat(ace.getMessage()).isEqualTo("Please check account details.");
        }
    }


    @Test
    public void validate_should_throwException_when_transferBetweenSameAccount() throws Exception {
        final Account accountFrom = new Account("Id-1", new BigDecimal("20.00"));
        final Account accountTo = new Account("Id-1");      
        try {
            transferValidator.validate(accountFrom, accountTo);
            fail("Same account transfer");
        } catch (TransferBetweenSameAccountException sae) {
            assertThat(sae.getMessage()).isEqualTo("Transfer to self not permitted.");
        }
    }


   

}
