package org.example.service;
import org.example.model.Customer;

import java.util.List;

public class CustomerService {
    private List<Customer> customers;
    private CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void readCustomers(){
        customerDAO.readCustomers();
    }
}
