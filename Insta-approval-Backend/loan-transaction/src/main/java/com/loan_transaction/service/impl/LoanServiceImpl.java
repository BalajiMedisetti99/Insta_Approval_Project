package com.loan_transaction.service.impl;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.loan_transaction.client.CustomerClient;
import com.loan_transaction.constants.ErrorMessages;
import com.loan_transaction.domain.DocumentType;
import com.loan_transaction.domain.LoanStatus;
import com.loan_transaction.dto.LoanApplicationRequestDTO;
import com.loan_transaction.dto.LoanApplicationResponseDTO;
import com.loan_transaction.dto.LoanTypeRequestDTO;
import com.loan_transaction.dto.UserDTO;   // ✅ use UserDTO now
import com.loan_transaction.entity.Document;
import com.loan_transaction.entity.LoanApplication;
import com.loan_transaction.entity.LoanType;
import com.loan_transaction.exception.InvalidActionException;
import com.loan_transaction.exception.ResourceNotFoundException;
import com.loan_transaction.repositroy.DocumentRepository;
import com.loan_transaction.repositroy.LoanApplicationRepository;
import com.loan_transaction.repositroy.LoanTypeRepository;
import com.loan_transaction.service.LoanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanApplicationRepository loanRepo;
    private final LoanTypeRepository loanTypeRepo;
    private final CustomerClient customerClient;
    private final DocumentRepository documentRepo;
    private final ModelMapper modelMapper;

    private static final BigDecimal MAX_LOAN_AMOUNT = BigDecimal.valueOf(500_000);

    @Override
    public LoanApplicationResponseDTO applyForLoan(LoanApplicationRequestDTO request, String token) {
        validateLoanAmount(request.getLoanAmount());

        
        UserDTO user = customerClient.validateToken();


        LoanType loanType = loanTypeRepo.findById(request.getLoanTypeId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.LOAN_TYPE_NOT_FOUND));

        LoanApplication loan = LoanApplication.builder()
                .userId(user.getUserId())  // ✅ changed from getCustomerId() to getUserId()
                .loanType(loanType)
                .loanAmount(request.getLoanAmount())
                .applicationDate(LocalDateTime.now())
                .status(LoanStatus.PENDING)
                .remarks("Application submitted")
                .cibilSnapshot(user.getCibilScore())
                .build();

        loanRepo.save(loan);
        return modelMapper.map(loan, LoanApplicationResponseDTO.class);
    }

    @Override
    public LoanApplicationResponseDTO getLoanStatus(Long loanId) {
        LoanApplication loan = fetchLoanById(loanId);
        return modelMapper.map(loan, LoanApplicationResponseDTO.class);
    }

    @Override
    public LoanApplicationResponseDTO updateLoan(Long loanId, LoanApplicationRequestDTO request, String token) {
        LoanApplication loan = fetchLoanById(loanId);

        checkPendingStatus(loan);
        validateLoanAmount(request.getLoanAmount());

        LoanType loanType = loanTypeRepo.findById(request.getLoanTypeId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.LOAN_TYPE_NOT_FOUND));

        loan.setLoanAmount(request.getLoanAmount());
        loan.setLoanType(loanType);

        loanRepo.save(loan);
        return modelMapper.map(loan, LoanApplicationResponseDTO.class);
    }

    @Override
    public void cancelLoan(Long loanId, String token) {
        LoanApplication loan = fetchLoanById(loanId);

        checkPendingStatus(loan);
        loan.setStatus(LoanStatus.CANCELLED);
        loanRepo.save(loan);
    }

    public List<String> uploadDocuments(Long loanId, List<String> contents, List<DocumentType> types) {
        LoanApplication loan = fetchLoanById(loanId);
        checkPendingStatus(loan);

        if (contents.size() != types.size()) {
            throw new InvalidActionException("Contents and types must match in size");
        }

        List<String> uploadedContents = new ArrayList<>();
        for (int i = 0; i < contents.size(); i++) {
            Document doc = Document.builder()
                    .application(loan)
                    .documentType(types.get(i))
                    .fileContent(contents.get(i))  
                    .uploadDate(LocalDateTime.now())
                    .build();
            documentRepo.save(doc);
            uploadedContents.add(contents.get(i));
        }
        return uploadedContents;
    }

    public LoanType createLoanType(LoanTypeRequestDTO dto) {
        if (loanTypeRepo.existsByTypeName(dto.getTypeName())) {
            throw new RuntimeException("Loan type already exists!");
        }
        LoanType loanType = LoanType.builder()
                .typeName(dto.getTypeName())
                .description(dto.getDescription())
                .build();
        return loanTypeRepo.save(loanType);
    }

    public List<LoanType> getAllLoanTypes() {
        return loanTypeRepo.findAll();
    }

    private void validateLoanAmount(BigDecimal amount) {
        if (amount.compareTo(MAX_LOAN_AMOUNT) > 0) {
            throw new InvalidActionException(ErrorMessages.MAX_LOAN_AMOUNT_EXCEEDED);
        }
    }

    private void checkPendingStatus(LoanApplication loan) {
        if (loan.getStatus() != LoanStatus.PENDING) {
            throw new InvalidActionException("Operation allowed only for pending loans");
        }
    }

    private LoanApplication fetchLoanById(Long loanId) {
        return loanRepo.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.LOAN_NOT_FOUND + loanId));
    }
}
