package com.loan_transaction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.loan_transaction.domain.LoanStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name="loan_applications")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class LoanApplication {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal loanAmount;

    @Column(nullable = false)
    private LocalDateTime applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @Column(length = 1000)
    private String remarks; 

    private Integer cibilSnapshot;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;
}