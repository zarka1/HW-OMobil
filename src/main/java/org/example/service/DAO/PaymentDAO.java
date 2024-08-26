package org.example.service.DAO;

import org.example.model.Payment;

import java.util.List;

public interface PaymentDAO {
    public List<Payment> readPayments();
    public List<Payment> getPayments();
}
