package org.example.model;

public enum PaymentMethod {
    CARD,
    TRANSFER;

    public static PaymentMethod findByName(String name) {
        PaymentMethod result = null;
        for( PaymentMethod paymentMethod : values()) {
            if (paymentMethod.name().equalsIgnoreCase(name)) {
                result = paymentMethod;
                break;
            }
        }
        return result;
    }
}
