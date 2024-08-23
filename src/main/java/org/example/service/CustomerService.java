package org.example.service;
import org.example.model.Customer;

import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;
    private Logger logger;

    public CustomerService(CustomerDAO customerDAO, Logger logger) {
        this.customerDAO = customerDAO;
        this.logger = logger;
    }

    public void printCustomers(){
        for (Customer customer : customerDAO.readCustomers()){
            logger.logInfo(customer.toString());
        }
    }

}
