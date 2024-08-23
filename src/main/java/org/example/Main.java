package org.example;

import org.example.service.*;

public class Main {
    public static void main(String[] args) {
        Logger fileLogger = new FileLogger("./src/main/resources/application.log");
        Logger consoleLogger = new ConsoleLogger();
        CustomerDAO customerDAO = new CustomerDAOImpl("./src/main/resources/customer.csv", fileLogger);
        CustomerService customerService = new CustomerService(customerDAO, consoleLogger);
        customerService.printCustomers();
    }
}