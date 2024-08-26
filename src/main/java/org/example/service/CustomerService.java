package org.example.service;
import org.example.model.Customer;
import org.example.service.DAO.CustomerDAO;
import org.example.service.Logger.Logger;

public class CustomerService {
    private CustomerDAO customerDAO;
    private Logger logger;

    public CustomerService(CustomerDAO customerDAO, Logger logger) {
        this.customerDAO = customerDAO;
        this.logger = logger;
    }

    public void printCustomers(){
        for (Customer customer : customerDAO.getCustomers()){
            logger.logInfo(customer.toString());
        }
    }

}
