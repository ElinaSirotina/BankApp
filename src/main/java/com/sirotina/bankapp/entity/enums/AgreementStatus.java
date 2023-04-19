package com.sirotina.bankapp.entity.enums;

public enum AgreementStatus {
    ACTIVE("ACTIVE"),
    PENDING("PENDING"),
    REMOVED("REMOVED");
    private final String value;

    AgreementStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}