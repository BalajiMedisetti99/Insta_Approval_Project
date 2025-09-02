package com.loan_transaction.exception;

public class InvalidActionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidActionException(String message) {
        super(message);
    }

    public InvalidActionException(String message, Throwable cause) {
        super(message, cause);
    }
}