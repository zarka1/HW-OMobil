package org.example;

import org.example.service.CustomerDAO;
import org.example.service.CustomerDAOImpl;
import org.example.service.CustomerService;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAOImpl("./src/main/resources/customer.csv");
        CustomerService customerService = new CustomerService(customerDAO);
        customerService.readCustomers();
    }
}