package org.example;

import org.example.service.*;
import org.example.service.DAO.CustomerDAO;
import org.example.service.DAO.CustomerDAOImpl;
import org.example.service.DAO.PaymentDAO;
import org.example.service.DAO.PaymentDAOImpl;
import org.example.service.Logger.ConsoleLogger;
import org.example.service.Logger.FileLogger;

public class Main {
    private static final String APPLICATION_LOG = "./application.log";
    private static final String CUSTOMER_CSV = "./customer.csv";
    private static final String PAYMENT_CSV = "./payments.csv";

    public static void main(String[] args){
        FileLogger fileLogger = new FileLogger(APPLICATION_LOG); //readme-ben buildelest...
        ConsoleLogger consoleLogger = new ConsoleLogger();
        Bank.clearLogFiles(consoleLogger);
        CustomerDAO customerDAO = new CustomerDAOImpl(CUSTOMER_CSV, fileLogger, consoleLogger);
        PaymentDAO paymentDAO = new PaymentDAOImpl(PAYMENT_CSV,
                fileLogger, consoleLogger, customerDAO);
        PaymentService paymentService = new PaymentService(paymentDAO, customerDAO);
        paymentService.printPayments();
        Bank bank = new Bank(paymentService);
        bank.run();
    }
}