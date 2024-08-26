package org.example.model;

public class Customer {
    private WebshopId webShopId;
    private String customerId;
    private String customerName;
    private String customerAddress;
    private double totalSpending;

    public Customer(WebshopId webShopId, String customerId, String customerName, String customerAddress) {
        this.webShopId = webShopId;
        this.customerId = webShopId.toString() + "_" + customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "webShopId=" + webShopId +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                '}';
    }

    public boolean identified( String customerId){
        if(customerId.equals(this.customerId))
            return true;
        else return false;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(double totalSpending) {
        this.totalSpending = totalSpending;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }
}
