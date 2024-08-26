package org.example;

import org.example.service.*;
import org.example.service.DAO.CustomerDAO;
import org.example.service.DAO.CustomerDAOImpl;
import org.example.service.DAO.PaymentDAO;
import org.example.service.DAO.PaymentDAOImpl;
import org.example.service.Logger.ConsoleLogger;
import org.example.service.Logger.FileLogger;
import org.example.service.Logger.Logger;

public class Main {
    public static void main(String[] args){
        Logger fileLogger = new FileLogger("./src/main/resources/application.log");
        Logger consoleLogger = new ConsoleLogger();
        Bank.clearLogFiles(consoleLogger);
        CustomerDAO customerDAO = new CustomerDAOImpl("./src/main/resources/customer.csv", fileLogger);
        CustomerService customerService = new CustomerService(customerDAO, consoleLogger);
        PaymentDAO paymentDAO = new PaymentDAOImpl("./src/main/resources/payments.csv",
                fileLogger, customerDAO);
        PaymentService paymentService = new PaymentService(paymentDAO, consoleLogger, customerDAO);
        customerService.printCustomers();
        paymentService.printPayments();
        Bank bank = new Bank(customerService, paymentService);
        bank.run();
    }
}