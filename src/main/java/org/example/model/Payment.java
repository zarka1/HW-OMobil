package org.example.model;

import java.time.LocalDate;

public class Payment {
    private final String webShopId;
    private final java.lang.String customerId;
    private final PaymentMethod paymentMethod;
    private final Double amount;
    private Double accountNumber;
    private Double cardNumber;
    private final LocalDate paymentDate;

    public Payment(String webShopId, java.lang.String customerId, PaymentMethod paymentMethod, Double amount, Double accountNumber, Double cardNumber, LocalDate paymentDate) {
        this.webShopId = webShopId;//mindent final-re, ellenorzeseket a card vs. transfer-re
        this.customerId = webShopId + "_" + customerId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.paymentDate = paymentDate;
    }

    @Override
    public java.lang.String toString() {
        return "Payment{" +
                "webShopId='" + webShopId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", amount=" + amount +
                ", accountNumber=" + accountNumber +
                ", cardNumber=" + cardNumber +
                ", paymentDate=" + paymentDate +
                '}';
    }

    public void setAccountNumber(Double accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setCardNumber(Double cardNumber) {
        this.cardNumber = cardNumber;
    }//settert kivenni

    public java.lang.String getCustomerId() {
        return customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public String getWebShopId() {
        return webShopId;
    }
}
