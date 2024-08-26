package org.example.service.DAO;

import org.example.model.Customer;

import java.util.List;

public interface CustomerDAO {
    boolean checkIfExist(String customerId);
    List<Customer> getCustomers();
}
