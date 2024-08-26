package org.example.model;

public enum PaymentMethod {
    CARD,
    TRANSFER;

    public static PaymentMethod findByName(String name) {
        for( PaymentMethod paymentMethod : values()) {
            if (paymentMethod.name().equalsIgnoreCase(name)) {
                return paymentMethod;
            }
        }
        return null;
    }
}
