package com.loan_transaction.service;

import java.util.List;

import com.loan_transaction.dto.AdminActionDTO;
import com.loan_transaction.dto.LoanApplicationResponseDTO;

public interface AdminService {
	
	List<LoanApplicationResponseDTO> getPendingLoans();

    LoanApplicationResponseDTO approveLoan(Long loanId, AdminActionDTO actionDTO, String adminEmail);

    LoanApplicationResponseDTO rejectLoan(Long loanId, AdminActionDTO actionDTO, String adminEmail);

}
