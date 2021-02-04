package com.db.asset.management.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.db.asset.management.dao.AmountTransfer;
import com.db.asset.management.service.AccountsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class TestAccountController {

	@InjectMocks
	private AccountController accountController;

	@Mock
	private AccountsService accountService;

	@Test
	public void makeTransfer() throws JsonProcessingException, Exception {
		AmountTransfer transfer = new AmountTransfer("1", "2", BigDecimal.TEN);
	
		Mockito.when(accountService.makeTransfer(new AmountTransfer("1", "2", BigDecimal.TEN)))
				.thenReturn("successfully Transferred");
		//mockMvc.perform(put("/transfer").contentType(MediaType.APPLICATION_JSON)
			//	.content(objectMapper.writeValueAsString(transfer))).andExpect(status().isOk());

	}

}
