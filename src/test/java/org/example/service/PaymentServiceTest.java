package org.example.service;

import org.example.model.Customer;
import org.example.model.Payment;
import org.example.model.PaymentMethod;
import org.example.model.WebshopIncome;
import org.example.service.DAO.CustomerDAO;
import org.example.service.DAO.PaymentDAO;
import org.example.service.Logger.FileLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class PaymentServiceTest {
    Payment payment1;
    Payment payment2;
    Payment payment3;
    Customer cust1;
    Customer cust2;
    List<Payment> paymentlist;
    List<Customer> customerList;

    @BeforeEach
    public void initialize(){
        paymentlist = new ArrayList<>();
        cust1 = new Customer("WS01", "cust1", "cust1Name", "cust1address");
        cust1.setTotalSpending(100);
        cust2 = new Customer("WS01", "cust2", "cust2Name", "cust2address");
        cust2.setTotalSpending(200);
        customerList = new ArrayList<>(List.of(cust1, cust2));
        payment1 = new Payment("WS01", "cust1", PaymentMethod.CARD, 120.0,
                null, 2.34E12, LocalDate.now());
        payment2 = new Payment("WS01", "cust1", PaymentMethod.CARD, 17.0,
                null, 2.34E12, LocalDate.now());
        payment3 = new Payment("WS01", "cust2", PaymentMethod.TRANSFER, 178.0,
                null, 2.17E12, LocalDate.now());
        paymentlist.add(payment1);
        paymentlist.add(payment2);
        paymentlist.add(payment3);
    }
    @Test
    public void testTotalSpendingCalculationForCustomerValidInput(){
        double totalSpending = PaymentService.calculateTotalSpendingForCustomer(paymentlist, cust1);
        Assertions.assertEquals(137.0, totalSpending);
    }

    @Test
    public void testTotalSpendingCalculationForPaymentListIsNull(){
        List<Payment> paymentlist = null;
        Assertions.assertThrows(IllegalArgumentException.class, () ->PaymentService.calculateTotalSpendingForCustomer(paymentlist, cust1));

    }

    @Test
    public void testTotalSpendingCalculationForCustomerIsNull(){
        cust1 = null;
        Assertions.assertThrows(IllegalArgumentException.class, () ->PaymentService.calculateTotalSpendingForCustomer(paymentlist, cust1));

    }

    @Test
    public void saveTopSpendingCustomersToFileValidData(){
        CustomerDAO customerDAOmock = mock(CustomerDAO.class);
        when(customerDAOmock.getCustomers()).thenReturn(List.of(cust1, cust2));
        PaymentDAO paymentDAOmock = mock(PaymentDAO.class);
        PaymentService paymentService = new PaymentService(paymentDAOmock, customerDAOmock);
        FileLogger fileLoggermock = mock(FileLogger.class);
        paymentService.saveTopSpendingCustomersToFile(fileLoggermock, 1);
        verify(paymentDAOmock, times(1)).saveAllCustomersSpendingToFile(fileLoggermock, List.of(cust2));
    }

    @Test
    public void saveTopSpendingCustomersToFileFileLoggerIsNull(){
        CustomerDAO customerDAOmock = mock(CustomerDAO.class);
        FileLogger fileLogger = null;
        when(customerDAOmock.getCustomers()).thenReturn(List.of(cust1, cust2));
        PaymentDAO paymentDAOmock = mock(PaymentDAO.class);
        PaymentService paymentService = new PaymentService(paymentDAOmock, customerDAOmock);
        Assertions.assertThrows(IllegalArgumentException.class, () ->paymentService.saveTopSpendingCustomersToFile(fileLogger, 1));
    }

    @Test
    public void saveTopSpendingCustomersToFileNumberOfCustomersIsLessThanOne(){
        CustomerDAO customerDAOmock = mock(CustomerDAO.class);
        FileLogger fileLogger = mock(FileLogger.class);
        when(customerDAOmock.getCustomers()).thenReturn(List.of(cust1, cust2));
        PaymentDAO paymentDAOmock = mock(PaymentDAO.class);
        PaymentService paymentService = new PaymentService(paymentDAOmock, customerDAOmock);
        Assertions.assertThrows(IllegalArgumentException.class, () ->paymentService.saveTopSpendingCustomersToFile(fileLogger, 0));
    }

    @Test
    public void saveWebshopIncomesToFileValidData(){
        CustomerDAO customerDAOmock = mock(CustomerDAO.class);
        FileLogger fileLoggerMock = mock(FileLogger.class);
        PaymentDAO paymentDAOmock = mock(PaymentDAO.class);
        when(paymentDAOmock.getPayments()).thenReturn(paymentlist);
        PaymentService paymentService = new PaymentService(paymentDAOmock, customerDAOmock);
        paymentService.saveWebshopIncomesToFile(fileLoggerMock);
        WebshopIncome webshopIncome = new WebshopIncome("WS01");
        webshopIncome.addCardPaymentsAmount(137.0);
        webshopIncome.addTransferPaymentAmount(178.0);
        List<WebshopIncome> expected = List.of(webshopIncome);
        List<WebshopIncome> actual = paymentService.saveWebshopIncomesToFile(fileLoggerMock);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void saveWebshopIncomesToFileFileLoggerIsNull(){
        CustomerDAO customerDAOmock = mock(CustomerDAO.class);
        FileLogger fileLogger = null;
        PaymentDAO paymentDAOmock = mock(PaymentDAO.class);
        when(paymentDAOmock.getPayments()).thenReturn(paymentlist);
        PaymentService paymentService = new PaymentService(paymentDAOmock, customerDAOmock);
        Assertions.assertThrows(IllegalArgumentException.class, () ->paymentService.saveWebshopIncomesToFile(fileLogger));
    }

    @Test
    public void saveAllSpendingByCustomer(){
        CustomerDAO customerDAOmock = mock(CustomerDAO.class);
        FileLogger fileLoggerMock = mock(FileLogger.class);
        PaymentDAO paymentDAOmock = mock(PaymentDAO.class);
        when(paymentDAOmock.getPayments()).thenReturn(paymentlist);
        when(customerDAOmock.getCustomers()).thenReturn(customerList);
        PaymentService paymentService = new PaymentService(paymentDAOmock, customerDAOmock);
        List<Customer> actual = paymentService.saveAllSpendingByCustomer(fileLoggerMock);
        customerList.get(0).setTotalSpending(137.0);
        customerList.get(1).setTotalSpending(178.0);
        Assertions.assertTrue(actual.get(0).equals(customerList.get(0)));
        Assertions.assertTrue(actual.get(1).equals(customerList.get(1)));
    }

    @Test
    public void saveAllSpendingByCustomerFileLoggerIsNull(){
        CustomerDAO customerDAOmock = mock(CustomerDAO.class);
        FileLogger fileLogger = null;
        PaymentDAO paymentDAOmock = mock(PaymentDAO.class);
        PaymentService paymentService = new PaymentService(paymentDAOmock, customerDAOmock);
        Assertions.assertThrows(IllegalArgumentException.class, () ->paymentService.saveAllSpendingByCustomer(fileLogger));

    }
}