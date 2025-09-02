package com.loan_transaction.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.loan_transaction.constants.ErrorMessages;
import com.loan_transaction.domain.LoanStatus;
import com.loan_transaction.dto.AdminActionDTO;
import com.loan_transaction.dto.LoanApplicationResponseDTO;
import com.loan_transaction.entity.ApprovalHistory;
import com.loan_transaction.entity.LoanApplication;
import com.loan_transaction.exception.InvalidActionException;
import com.loan_transaction.exception.ResourceNotFoundException;
import com.loan_transaction.repositroy.ApprovalHistoryRepository;
import com.loan_transaction.repositroy.LoanApplicationRepository;
import com.loan_transaction.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final LoanApplicationRepository loanRepo;
    private final ApprovalHistoryRepository approvalRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<LoanApplicationResponseDTO> getPendingLoans() {
        return loanRepo.findByStatus(LoanStatus.PENDING).stream()
                .map(loan -> modelMapper.map(loan, LoanApplicationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LoanApplicationResponseDTO approveLoan(Long loanId, AdminActionDTO actionDTO, String adminEmail) {
        return processLoan(loanId, actionDTO, adminEmail, LoanStatus.APPROVED);
    }

    @Override
    public LoanApplicationResponseDTO rejectLoan(Long loanId, AdminActionDTO actionDTO, String adminEmail) {
        return processLoan(loanId, actionDTO, adminEmail, LoanStatus.REJECTED);
    }

    private LoanApplicationResponseDTO processLoan(Long loanId, AdminActionDTO actionDTO, String adminEmail, LoanStatus status) {
        LoanApplication loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.LOAN_NOT_FOUND + loanId));

        if (loan.getStatus() != LoanStatus.PENDING) {
            throw new InvalidActionException("Loan already processed");
        }

        loan.setStatus(status);
        loan.setRemarks(actionDTO.getRemarks());
        loanRepo.save(loan);

        approvalRepo.save(ApprovalHistory.builder()
                .application(loan)
                .adminEmail(adminEmail)
                .action(status)
                .remarks(actionDTO.getRemarks())
                .actionDate(LocalDateTime.now())
                .build());

        return modelMapper.map(loan, LoanApplicationResponseDTO.class);
    }
}
