package com.loan_transaction.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.loan_transaction.domain.DocumentType;
import com.loan_transaction.dto.LoanApplicationRequestDTO;
import com.loan_transaction.dto.LoanApplicationResponseDTO;
import com.loan_transaction.dto.LoanTypeRequestDTO;
import com.loan_transaction.entity.LoanType;

public interface LoanService {
	
	LoanApplicationResponseDTO applyForLoan(LoanApplicationRequestDTO request, String token);

    LoanApplicationResponseDTO getLoanStatus(Long loanId);

    LoanApplicationResponseDTO updateLoan(Long loanId, LoanApplicationRequestDTO request, String token);

    void cancelLoan(Long loanId, String token);

    List<String> uploadDocuments(Long loanId, List<String> contents, List<DocumentType> types);

    
    LoanType createLoanType(LoanTypeRequestDTO request);
    
    List<LoanType> getAllLoanTypes();
}
