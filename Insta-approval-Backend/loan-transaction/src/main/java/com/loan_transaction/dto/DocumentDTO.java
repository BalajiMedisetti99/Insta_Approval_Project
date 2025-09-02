package com.loan_transaction.dto;



import com.loan_transaction.domain.DocumentType;

import lombok.Data;

@Data
public class DocumentDTO {
	private String fileContent;

    private DocumentType documentType;
}