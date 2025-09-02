package com.loan_transaction.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanApplicationRequestDTO {
	
	@NotNull
    private Long userId;
	
	@NotNull 
	@DecimalMin(value="1.00", inclusive=true, message = "Loan amount must be greater than 0")
    private BigDecimal loanAmount;

    @NotNull
    private Long loanTypeId;
}