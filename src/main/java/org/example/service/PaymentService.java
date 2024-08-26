package org.example.service;

import org.example.model.*;
import org.example.service.DAO.CustomerDAO;
import org.example.service.DAO.PaymentDAO;
import org.example.service.Logger.ConsoleLogger;
import org.example.service.Logger.FileLogger;
import org.example.service.Logger.Logger;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private final CustomerDAO customerDAO;
    private final PaymentDAO paymentDAO;
    private static final Logger consoleLogger = new ConsoleLogger();

    public PaymentService(PaymentDAO paymentDAO, CustomerDAO customerDAO) {
        this.paymentDAO = paymentDAO;
        this.customerDAO = customerDAO;
    }

    public void printPayments(){
        for (Payment payment : paymentDAO.getPayments()){
            consoleLogger.logInfo(payment.toString());
        }
    }

    public List<Customer> saveAllSpendingByCustomer(FileLogger filelogger){
        if(filelogger == null) {
            consoleLogger.logError("Invalid parameters", "");
            throw new IllegalArgumentException("Filelogger cannot be null.");
        }
        List<Payment> payments = paymentDAO.getPayments();
        List<Customer> customers = customerDAO.getCustomers();
        for (Customer customer : customers) {
            double totalSpending = calculateTotalSpendingForCustomer(payments, customer);
            customer.setTotalSpending(totalSpending);
        }
        paymentDAO.saveAllCustomersSpendingToFile(filelogger, customers);
        return customers;
    }

    public static double calculateTotalSpendingForCustomer(List<Payment> payments, Customer customer) {
        if(payments == null || customer == null) {
            consoleLogger.logError("Invalid parameters", "");
            throw new IllegalArgumentException("Payments and Customer cannot be null.");
        }
        double totalSpending = 0;
        for (Payment payment : payments){
            if ((payment.getCustomerId()).equals(customer.getCustomerId())){
                totalSpending += payment.getAmount();
            }
        }
        return totalSpending;
    }

    public void saveTopSpendingCustomersToFile(FileLogger fileLogger, int numberOfTopCustomers){
        if(fileLogger == null || numberOfTopCustomers < 1) {
            consoleLogger.logError("Invalid parameters", "");
            throw new IllegalArgumentException("Filelogger cannot be null, numberOfTopCustomers" +
                    "must be greater than 0.");
        }
        List<Customer> customers = new ArrayList<>(customerDAO.getCustomers());
        List<Customer> topCustomers = calculateTopCustomers(numberOfTopCustomers, customers);
        paymentDAO.saveAllCustomersSpendingToFile(fileLogger, topCustomers);
    }

    private static List<Customer> calculateTopCustomers(int numberOfTopCustomers, List<Customer> customers) {
        List<Customer> topCustomers = new ArrayList<>();
        for(int i = 0; i < numberOfTopCustomers; i++){
            double maxAmount = customers.get(0).getTotalSpending();
            int maxItem = 0;
            for(int j = 1; j < customers.size(); j++){
                if (customers.get(j).getTotalSpending() > maxAmount){
                    maxAmount = customers.get(j).getTotalSpending();
                    maxItem = j;
                }
            }
            topCustomers.add(customers.get(maxItem));
            customers.remove(maxItem);
        }
        return topCustomers;
    }

    public List<WebshopIncome> saveWebshopIncomesToFile(FileLogger fileLogger){
        if(fileLogger == null) {
            consoleLogger.logError("Invalid parameters", "");
            throw new IllegalArgumentException("Filelogger cannot be null.");
        }
        List<WebshopIncome> webshopIncomes = createWebshopIncomes();
        for (Payment payment : paymentDAO.getPayments()){
            for (WebshopIncome webshopIncome : webshopIncomes) {
                if (payment.getWebShopId().equals(webshopIncome.getWebshopId())) {
                    if (payment.getPaymentMethod() == PaymentMethod.CARD) {
                        webshopIncome.addCardPaymentsAmount(payment.getAmount());
                    } else {
                        webshopIncome.addTransferPaymentAmount(payment.getAmount());
                    }
                }
            }
        }
        paymentDAO.saveWebshopIncomesToFile(fileLogger, webshopIncomes);
        return webshopIncomes;
    }

    private List<WebshopIncome> createWebshopIncomes() {
        List<WebshopIncome> webshopIncomes = new ArrayList<>();
        List<String> webshopList = new ArrayList<>();
        for (Payment payment : paymentDAO.getPayments()){
            if(!webshopList.contains(payment.getWebShopId())){
                webshopList.add(payment.getWebShopId());
            }
        }
        for (String string : webshopList){
            WebshopIncome webshopIncome = new WebshopIncome(string);
            webshopIncomes.add(webshopIncome);
        }
        return webshopIncomes;
    }
}
