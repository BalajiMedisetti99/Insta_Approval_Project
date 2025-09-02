package com.loan_transaction.dto;

import com.loan_transaction.domain.LoanStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminActionDTO {
		
		@NotNull(message = "adminId is required")
	 	private Long adminId;

	    @NotNull(message = "action is required and must be APPROVE or REJECT")
	    private LoanStatus action;

	    @Size(max = 1000, message = "remarks can't exceed 1000 chars")
	    private String remarks;
}