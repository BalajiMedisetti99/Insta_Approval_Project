package com.loan_transaction.entity;

import java.time.LocalDateTime;

import com.loan_transaction.domain.LoanStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name="approval_history")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class ApprovalHistory {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approvalId;

    @ManyToOne(optional = false)
    @JoinColumn(name="application_id")
    private LoanApplication application;

    private String adminEmail;
    
    @Enumerated(EnumType.STRING)
    private LoanStatus action;

    private LocalDateTime actionDate;

    @Column(length = 1000)
    private String remarks;
}
