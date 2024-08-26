package org.example.service;

import org.example.model.*;
import org.example.service.DAO.CustomerDAO;
import org.example.service.DAO.PaymentDAO;
import org.example.service.Logger.FileLogger;
import org.example.service.Logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private CustomerDAO customerDAO;
    private PaymentDAO paymentDAO;
    private Logger consolelogger;

    public PaymentService(PaymentDAO paymentDAO, Logger consolelogger, CustomerDAO customerDAO) {
        this.paymentDAO = paymentDAO;
        this.consolelogger = consolelogger;
        this.customerDAO = customerDAO;
    }

    public void printPayments(){
        for (Payment payment : paymentDAO.getPayments()){
            consolelogger.logInfo(payment.toString());
        }
    }

    public void saveAllSpendingByCustomer(FileLogger filelogger){
        List<Payment> payments = paymentDAO.getPayments();
        List<Customer> customers = customerDAO.getCustomers();
        for(int i = 0; i < customers.size(); i++){
            double totalSpending = calculateTotalSpendingForCustomer(payments, customers.get(i));
            customers.get(i).setTotalSpending(totalSpending);
        }
        saveAllCustomersSpendingToFile(filelogger, customers);
    }

    public static double calculateTotalSpendingForCustomer(List<Payment> payments, Customer customer) {
        double totalSpending = 0;
        for (Payment payment : payments){
            if ((payment.getCustomerId()).equals(customer.getCustomerId())){
                totalSpending += payment.getAmount();
            }
        }
        return totalSpending;
    }

    private static void saveAllCustomersSpendingToFile(FileLogger fileLogger, List<Customer> customers){
        for (Customer customer : customers){
            fileLogger.logInfo(customer.getCustomerName() +";" +
                    customer.getCustomerAddress() +";" +
                    customer.getTotalSpending());
        }
    }

    public void saveTopSpendingCustomersToFile(FileLogger fileLogger, int numberOfTopCustomers){
        List<Customer> customers = new ArrayList<>(customerDAO.getCustomers());
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
        saveAllCustomersSpendingToFile(fileLogger, topCustomers);
    }

    public void saveWebshopIncomes(FileLogger fileLogger){
        List<WebshopIncome> webshopIncomes = new ArrayList<>();
        for (WebshopId webshopId : WebshopId.values()){
            WebshopIncome webshopIncome = new WebshopIncome(webshopId);
            webshopIncomes.add(webshopIncome);
        }
        for (Payment payment : paymentDAO.getPayments()){
            for (int i = 0; i < webshopIncomes.size(); i++){
                if(payment.getWebShopId() == webshopIncomes.get(i).getWebshopId()){
                    if(payment.getPaymentMethod() == PaymentMethod.CARD){
                        webshopIncomes.get(i).addCardPaymentsAmount(payment.getAmount());
                    } else {
                        webshopIncomes.get(i).addTrasferPaymentAmount(payment.getAmount());
                    }
                }
            }
        }
        saveWebshopIncomesToFile(fileLogger, webshopIncomes);
    }

    private static void saveWebshopIncomesToFile(FileLogger fileLogger, List<WebshopIncome> webshopIncomes){
        for(WebshopIncome webshopIncome : webshopIncomes){
            fileLogger.logInfo(webshopIncome.getWebshopId().toString() + ";"
            + webshopIncome.getCardPayments() + ";"
            + webshopIncome.getTransferPayments());
        }
    }
}
