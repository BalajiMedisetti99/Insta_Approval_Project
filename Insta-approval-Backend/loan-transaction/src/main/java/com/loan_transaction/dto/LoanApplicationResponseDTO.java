package com.loan_transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class LoanApplicationResponseDTO {
	private Long applicationId;
	private Long userId;
	private String loanTypeName;
	private BigDecimal loanAmount;
	private LocalDateTime applicationDate;
	private String status;
	private String remarks;
	private Integer cibilSnapshot;
	private List<String> documents;
}
