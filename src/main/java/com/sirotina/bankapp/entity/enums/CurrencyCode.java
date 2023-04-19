package com.sirotina.bankapp.entity.enums;

public enum CurrencyCode {
    EUR("EUR"),
    USD("USD"),
    UAH("UAH"),
    RUB("RUB");

    private final String value;

    CurrencyCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
