package com.sirotina.bankapp.entity.enums;

public enum AccountType {
    CREDIT("CREDIT"),
    DEPOSIT("DEPOSIT"),
    CURRENT("CURRENT");
    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}