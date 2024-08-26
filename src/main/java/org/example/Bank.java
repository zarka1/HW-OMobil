package org.example;

import org.example.service.CustomerService;
import org.example.service.Logger.FileLogger;
import org.example.service.Logger.Logger;
import org.example.service.PaymentService;

import java.io.FileOutputStream;
import java.io.IOException;

public class Bank {
    private CustomerService customerService;
    private PaymentService paymentService;
    private Logger consoleLogger;
    private static final int NUMBER_OF_TOP_CUSTOMERS = 2;

    public Bank(CustomerService customerService, PaymentService paymentService) {
        this.customerService = customerService;
        this.paymentService = paymentService;
    }

    public void run()  {
        saveAllSpendingByCustomer();
        saveTopCustomerSpending();
        saveWebshopIncomes();
    }

    public static void clearLogFiles(Logger consoleLogger) {
        try{// clear the logfile contents
            new FileOutputStream("./src/main/resources/report01.csv").close();
            new FileOutputStream("./src/main/resources/top.csv").close();
            new FileOutputStream("./src/main/resources/application.log").close();
            new FileOutputStream("./src/main/resources/report02.csv").close();
        } catch (IOException e){
            consoleLogger.logError("Logfile reading error(application.log or report01.csv," +
                    " top.csv, or report02.csv", "");
        }
    }

    private void saveAllSpendingByCustomer(){
        FileLogger report01 = new FileLogger("./src/main/resources/report01.csv");
        paymentService.saveAllSpendingByCustomer(report01);
    }

    private void saveTopCustomerSpending(){
        FileLogger top = new FileLogger("./src/main/resources/top.csv");
        paymentService.saveTopSpendingCustomersToFile(top, NUMBER_OF_TOP_CUSTOMERS);
    }

    private void saveWebshopIncomes(){
        FileLogger report02 = new FileLogger("./src/main/resources/report02.csv");
        paymentService.saveWebshopIncomes(report02);
    }
}
