package org.example.model;

import java.util.Objects;

public class Customer {
    private String webShopId;// String WS+szamra ellenorzes
    private java.lang.String customerId;
    private java.lang.String customerName;
    private java.lang.String customerAddress;
    private double totalSpending;

    public Customer(String webShopId, java.lang.String customerId, java.lang.String customerName, java.lang.String customerAddress) {
        this.webShopId = webShopId;
        this.customerId = webShopId + "_" + customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
    }

    @Override
    public java.lang.String toString() {
        return "Customer{" +
                "webShopId=" + webShopId +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                '}';
    }

    public boolean hasCustomerId(java.lang.String customerId){
        return customerId.equals(this.customerId);
    }

    public java.lang.String getCustomerId() {
        return customerId;
    }

    public double getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(double totalSpending) {
        this.totalSpending = totalSpending;
    }

    public java.lang.String getCustomerName() {
        return customerName;
    }

    public java.lang.String getCustomerAddress() {
        return customerAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Double.compare(customer.totalSpending, totalSpending) == 0 && Objects.equals(webShopId, customer.webShopId) && Objects.equals(customerId, customer.customerId) && Objects.equals(customerName, customer.customerName) && Objects.equals(customerAddress, customer.customerAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(webShopId, customerId, customerName, customerAddress, totalSpending);
    }
}
