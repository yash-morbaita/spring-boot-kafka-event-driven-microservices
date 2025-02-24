package com.codewithyash.orderservice.utility;

public enum TaskEnum {

    SENDNEWORDEREVENT("SendNewOrderEventTask"),
    VALIDATEINPUTPARAMETERSREQUEST("ValidateInputParametersRequestTask");

    private final String values;

    TaskEnum(String values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return values;
    }
}
