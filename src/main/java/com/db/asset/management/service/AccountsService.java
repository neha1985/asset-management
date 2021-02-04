package com.db.asset.management.service;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.asset.management.dao.Account;
import com.db.asset.management.dao.AccountUpdate;
import com.db.asset.management.dao.AmountTransfer;
import com.db.asset.management.exception.AccountNotFoundException;
import com.db.asset.management.exception.NotEnoughFundsException;
import com.db.asset.management.exception.TransferBetweenSameAccountException;
import com.db.asset.management.repository.AccountRepository;

@Service
public class AccountsService {

	@Autowired
	private AccountRepository accountsRepository;

	//@Getter
	//private final NotificationService notificationService;

	@Autowired
	private TransferValidator transferValidator;
	
	public void createAccount(Account account) {
		 accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {	
		Account account = accountsRepository.getAccount(accountId);
		if(null == account) {
			throw new AccountNotFoundException("Account " + accountId + " does not exist.");
		}
		 return account;
	}

	/**
	 * Makes a transfer between two accounts for the balance specified by the
	 * {@link Transfer} object
	 * 
	 * @param transfer
	 * @throws AccountNotFoundException            When an account does not exist
	 * @throws NotEnoughFundsException             When there are not enough funds
	 *                                             to complete the transfer
	 * @throws TransferBetweenSameAccountException Transfer to self account is not
	 *                                             permitted
	 */
	public String makeTransfer(AmountTransfer transfer)
			throws AccountNotFoundException, NotEnoughFundsException, TransferBetweenSameAccountException {

		final Account accountFrom = getAccount(transfer.getAccountFromId());
		final Account accountTo = getAccount(transfer.getAccountToId());
		final BigDecimal amount = transfer.getAmount();

		transferValidator.validate(accountFrom, accountTo, transfer);
		boolean successful = false;
		synchronized (accountFrom.getAccountId()) {
			synchronized (accountTo.getAccountId()) {
				if (!enoughFunds(accountFrom, transfer.getAmount())){
		            throw new NotEnoughFundsException("Not enough funds on account " + accountFrom.getAccountId() + ". Please check your balance.");
		        }
				successful = accountsRepository.updateAccounts(
						Arrays.asList(new AccountUpdate(accountFrom.getAccountId(), amount.negate()),
								new AccountUpdate(accountTo.getAccountId(), amount)));				
			}
		}
		if (successful) {
			//TODO Notification Service provided by other 
			 //notificationService.notifyAboutTransfer(accountFrom, "The transfer to the account with ID " + accountTo.getAccountId() + " is now complete for the amount of " + transfer.getAmount() + ".");
			 //notificationService.notifyAboutTransfer(accountTo, "The account with ID + " + accountFrom.getAccountId() + " has transferred " + transfer.getAmount() + " into your account.");
			return "Transferred Successfully";
			
		}
		return "Error occured";
		
	}
	
	 private boolean enoughFunds(final Account account, final BigDecimal amount) {
	        return account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
	    }

}
