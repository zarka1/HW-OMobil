package org.example.service;

import org.example.model.Customer;
import org.example.model.Payment;
import org.example.model.PaymentMethod;
import org.example.model.WebshopId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class PaymentServiceTest {
    @Test
    public void testTotalSpendingCalculationForCustomer(){
        List<Payment> paymentlist = new ArrayList<>();
        Customer cust1 = new Customer(WebshopId.WS01, "cust1", "Roland", "Kecskemet");
        Payment payment1 = new Payment(WebshopId.WS01, "cust1", PaymentMethod.CARD, 120.0,
                null, 2.34E12, LocalDate.now());
        Payment payment2 = new Payment(WebshopId.WS01, "cust1", PaymentMethod.CARD, 17.0,
                null, 2.34E12, LocalDate.now());
        Payment payment3 = new Payment(WebshopId.WS01, "cust2", PaymentMethod.CARD, 178.0,
                null, 2.17E12, LocalDate.now());
        paymentlist.add(payment1);
        paymentlist.add(payment2);
        paymentlist.add(payment3);
        double totalSpending = PaymentService.calculateTotalSpendingForCustomer(paymentlist, cust1);
        Assertions.assertEquals(137.0, totalSpending);
    }
}