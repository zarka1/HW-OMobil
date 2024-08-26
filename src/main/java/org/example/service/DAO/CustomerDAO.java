package org.example.service;

import org.example.model.Customer;

import java.util.List;

public interface CustomerDAO {
    public List<Customer> readCustomers();
    public boolean checkIfExist(String customerId);
    public List<Customer> getCustomers();
}
