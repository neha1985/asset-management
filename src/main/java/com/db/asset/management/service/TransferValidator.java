package com.db.asset.management.service;

import org.springframework.stereotype.Component;

import com.db.asset.management.dao.Account;
import com.db.asset.management.dao.AmountTransfer;
import com.db.asset.management.exception.AccountNotFoundException;
import com.db.asset.management.exception.NotEnoughFundsException;
import com.db.asset.management.exception.TransferBetweenSameAccountException;

@Component
public class TransferValidator {

    /**
     * Validates whether the accounts exist, that a transfer cannot happen between same accounts and
     * that there are enough funds to complete the transfer.
     *
     * @param accountFrom source account
     * @param accountTo destination account
     * @param transfer The transfer object as requested
     * @throws AccountNotFoundException
     * @throws NotEnoughFundsException
     * @throws TransferBetweenSameAccountException
     */
    public void validate(final Account accountFrom, final Account accountTo, final AmountTransfer transfer)
            throws AccountNotFoundException, NotEnoughFundsException, TransferBetweenSameAccountException{

        if (null == accountFrom || null == accountTo){
            throw new AccountNotFoundException("Please check account details.");
        }

        if (sameAccount(transfer)){
            throw new TransferBetweenSameAccountException("Transfer to self not permitted.");
        }
        
    }

    private boolean sameAccount(final AmountTransfer transfer) {
        return transfer.getAccountFromId().equals(transfer.getAccountToId());
    }


   

}
