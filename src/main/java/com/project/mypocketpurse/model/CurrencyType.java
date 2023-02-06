package com.project.mypocketpurse.model;

public enum CurrencyType {
    RUB("Рубль"),
    DOLLAR("Доллар");

    private final String currencyTypeText;

    CurrencyType(String currencyTypeText) {
        this.currencyTypeText = currencyTypeText;
    }

    public String getCurrencyTypeText() {
        return this.currencyTypeText;
    }
}
