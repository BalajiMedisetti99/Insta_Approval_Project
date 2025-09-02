package com.loan_transaction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loan_transaction.domain.DocumentType;
import com.loan_transaction.dto.LoanApplicationRequestDTO;
import com.loan_transaction.dto.LoanApplicationResponseDTO;
import com.loan_transaction.dto.LoanTypeRequestDTO;
import com.loan_transaction.entity.LoanType;
import com.loan_transaction.service.LoanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    
    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationResponseDTO> applyForLoan(
            @RequestBody LoanApplicationRequestDTO request,
            @RequestHeader("Authorization") String token) {

        LoanApplicationResponseDTO response = loanService.applyForLoan(request, token);
        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/{loanId}/status")
    public ResponseEntity<LoanApplicationResponseDTO> getLoanStatus(@PathVariable Long loanId) {
        LoanApplicationResponseDTO response = loanService.getLoanStatus(loanId);
        return ResponseEntity.ok(response);
    }

   
    @PutMapping("/{loanId}")
    public ResponseEntity<LoanApplicationResponseDTO> updateLoan(
            @PathVariable Long loanId,
            @RequestBody LoanApplicationRequestDTO request,
            @RequestHeader("Authorization") String token) {

        LoanApplicationResponseDTO response = loanService.updateLoan(loanId, request, token);
        return ResponseEntity.ok(response);
    }

    
    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> cancelLoan(
            @PathVariable Long loanId,
            @RequestHeader("Authorization") String token) {

        loanService.cancelLoan(loanId, token);
        return ResponseEntity.noContent().build();
    }

    
    @PostMapping("/{loanId}/documents")
    public ResponseEntity<List<String>> uploadDocuments(
            @PathVariable Long loanId,
            @RequestParam("contents") List<String> contents,  
            @RequestParam("types") List<DocumentType> types,
            @RequestHeader("Authorization") String token) {

        List<String> uploadedContents = loanService.uploadDocuments(loanId, contents, types);
        return ResponseEntity.ok(uploadedContents);
    }

    
    @PostMapping("/type")
    public LoanType createLoanType(@RequestBody LoanTypeRequestDTO dto) {
        return loanService.createLoanType(dto);
    }

    @GetMapping("/types")
    public List<LoanType> getAllLoanTypes() {
        return loanService.getAllLoanTypes();
    }
}
