package com.loan_transaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.loan_transaction.dto.UserDTO;

@FeignClient(
		name = "user-service", 
		url = "http://localhost:8081/api/v1/customers",
		configuration = com.loan_transaction.config.FeignConfig.class)
public interface CustomerClient {
	@GetMapping("/validate")
	UserDTO validateToken();

}