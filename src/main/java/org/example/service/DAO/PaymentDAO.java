package org.example.service.DAO;

import org.example.model.Customer;
import org.example.model.Payment;
import org.example.model.WebshopIncome;
import org.example.service.Logger.FileLogger;

import java.util.List;

public interface PaymentDAO {
    List<Payment> readPayments();
    List<Payment> getPayments();
    void saveWebshopIncomesToFile(FileLogger fileLogger, List<WebshopIncome> webshopIncomes);
    void saveAllCustomersSpendingToFile(FileLogger fileLogger, List<Customer> customers);
}
