package org.example.model;

public class WebshopIncome {
    private WebshopId webshopId;
    private double cardPayments = 0.0;
    private double transferPayments = 0.0;

    public WebshopIncome(WebshopId webshopId) {
        this.webshopId = webshopId;
    }

    public void addCardPaymentsAmount(double amount){
        cardPayments += amount;
    }

    public void addTrasferPaymentAmount(double amount){
        transferPayments += amount;
    }

    public WebshopId getWebshopId() {
        return webshopId;
    }

    public double getCardPayments() {
        return cardPayments;
    }

    public double getTransferPayments() {
        return transferPayments;
    }
}
