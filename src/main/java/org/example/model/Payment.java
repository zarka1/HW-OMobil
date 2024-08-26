package org.example.model;

import java.time.LocalDate;

public class Payment {
    private WebshopId webShopId;
    private String customerId;
    private PaymentMethod paymentMethod;
    private Double amount;
    private Double accountNumber;
    private Double cardNumber;
    private LocalDate paymentDate;

    public Payment(WebshopId webShopId, String customerId, PaymentMethod paymentMethod, Double amount, Double accountNumber, Double cardNumber, LocalDate paymentDate) {
        this.webShopId = webShopId;
        this.customerId = webShopId.toString() + "_" + customerId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
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
    }

    public String getCustomerId() {
        return customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public WebshopId getWebShopId() {
        return webShopId;
    }
}
