package com.sirotina.bankapp.service.exception;

public class TransactionNotFoundException extends Exception {

    public TransactionNotFoundException(String message) {
        super(message);
    }

}
