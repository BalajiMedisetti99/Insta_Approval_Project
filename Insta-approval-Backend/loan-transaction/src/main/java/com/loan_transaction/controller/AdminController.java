package com.loan_transaction.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loan_transaction.client.CustomerClient;
import com.loan_transaction.dto.AdminActionDTO;
import com.loan_transaction.dto.UserDTO;
import com.loan_transaction.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/loans")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final CustomerClient customerClient;

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingLoans(@RequestHeader("Authorization") String token) {
        UserDTO user = customerClient.validateToken();
        return ResponseEntity.ok(adminService.getPendingLoans());
    }

    @PostMapping("/{loanId}/approve")
    public ResponseEntity<?> approveLoan(
            @PathVariable Long loanId,
            @RequestBody AdminActionDTO actionDTO,
            @RequestHeader("Authorization") String token) {

        UserDTO user = customerClient.validateToken();
        if (!user.getRoles().contains("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admin role required");
        }
        return ResponseEntity.ok(adminService.approveLoan(loanId, actionDTO, user.getEmail()));
    }

    @PostMapping("/{loanId}/reject")
    public ResponseEntity<?> rejectLoan(
            @PathVariable Long loanId,
            @RequestBody AdminActionDTO actionDTO,
            @RequestHeader("Authorization") String token) {

        UserDTO user = customerClient.validateToken();
        if (!user.getRoles().contains("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admin role required");
        }
        return ResponseEntity.ok(adminService.rejectLoan(loanId, actionDTO, user.getEmail()));
    }
}



