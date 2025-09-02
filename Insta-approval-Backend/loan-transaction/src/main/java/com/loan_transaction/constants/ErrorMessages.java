package com.loan_transaction.constants;

public final class ErrorMessages {

    private ErrorMessages() { throw new IllegalStateException("Utility class"); }

    public static final String LOAN_NOT_FOUND = "Loan application not found with id: ";
    public static final String DOCUMENT_NOT_FOUND = "Document not found with id: ";
    public static final String INVALID_ACTION = "Invalid action for loan application: ";
    public static final String MAX_LOAN_AMOUNT_EXCEEDED = "Loan amount exceeds maximum allowed: 500000";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found with id: ";
    
    public static final String STORAGE_DIR_CREATION_FAILED = "Could not create storage directory";
    public static final String FILE_STORAGE_FAILED = "Could not store file: ";
	public static final String DOCUMENT_UPLOAD_NOT_ALLOWED = "Document upload not allowed for loan application in status: ";
	public static final String LOAN_TYPE_NOT_FOUND = "Loan type not found: ";
}
