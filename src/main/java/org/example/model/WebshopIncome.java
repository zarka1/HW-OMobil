package org.example.model;

import java.util.Objects;

public class WebshopIncome {
    private final String string;
    private double cardPayments = 0.0;
    private double transferPayments = 0.0;

    public WebshopIncome(String string) {
        this.string = string;
    }

    public void addCardPaymentsAmount(double amount){
        cardPayments += amount;
    }

    public void addTransferPaymentAmount(double amount){
        transferPayments += amount;
    }

    public String getWebshopId() {
        return string;
    }

    public double getCardPayments() {
        return cardPayments;
    }

    public double getTransferPayments() {
        return transferPayments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebshopIncome that)) return false;
        return Double.compare(that.cardPayments, cardPayments) == 0 && Double.compare(that.transferPayments, transferPayments) == 0 && Objects.equals(string, that.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string, cardPayments, transferPayments);
    }
}
