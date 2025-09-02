package com.loan_transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.loan_transaction.client")
public class LoanTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanTransactionApplication.class, args);
	}

}
