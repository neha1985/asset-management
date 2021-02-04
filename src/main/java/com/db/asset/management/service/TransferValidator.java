package com.db.asset.management.service;

import org.springframework.stereotype.Component;

import com.db.asset.management.dao.Account;
import com.db.asset.management.exception.AccountNotFoundException;
import com.db.asset.management.exception.TransferBetweenSameAccountException;

@Component
public class TransferValidator {

    /**
     * Validates whether the accounts exist and that a transfer cannot happen between same accounts.
     *
     * @param accountFrom source account
     * @param accountTo destination account
     * @throws AccountNotFoundException
     * @throws TransferBetweenSameAccountException
     */
    public void validate(final Account accountFrom, final Account accountTo)
            throws AccountNotFoundException, TransferBetweenSameAccountException{

        if (null == accountFrom || null == accountTo){
            throw new AccountNotFoundException("Please check account details.");
        }

        if (accountFrom.getAccountId().equals(accountTo.getAccountId())){
            throw new TransferBetweenSameAccountException("Transfer to self not permitted.");
        }
    }

}
