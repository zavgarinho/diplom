package com.zavga.diplom.entity.customer;

public enum CustomerType {
    REGULAR("Фізична особа"),
    LEGAL("Юридична особа");



    private final String label;
    private CustomerType(String label){
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
