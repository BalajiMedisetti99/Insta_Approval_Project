package com.loan_transaction.entity;

import java.time.LocalDateTime;

import com.loan_transaction.domain.DocumentType;

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
@Table(name="documents")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Document {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @ManyToOne(optional = false)
    @JoinColumn(name="application_id")
    private LoanApplication application;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String fileContent; 


    @Column(nullable = false)
    private LocalDateTime uploadDate;
}
