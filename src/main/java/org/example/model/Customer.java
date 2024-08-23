package org.example.model;

public class Customer {
    private WebshopId webShopId;
    private String customerId;
    private String customerName;
    private String customerAddress;

    public Customer(WebshopId webShopId, String customerId, String customerName, String customerAddress) {
        this.webShopId = webShopId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
    }
}
