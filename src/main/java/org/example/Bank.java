package org.example;


import org.example.service.Logger.ConsoleLogger;
import org.example.service.Logger.FileLogger;
import org.example.service.PaymentService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bank {
    private final PaymentService paymentService;
    private static final int NUMBER_OF_TOP_CUSTOMERS = 2;
    private static final String REPORT01_CSV = "./report01.csv";
    private static final String TOP_CSV = "./top.csv";
    private static final String APPLICATION_LOG = "./application.log";
    private static final String REPORT02_CSV = "./report02.csv";

    public Bank(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void run()  {
        saveAllSpendingByCustomer();
        saveTopCustomerSpending();
        saveWebshopIncomes();
    }

    /**
     * Clearing logfile contents.
     * @param consoleLogger
     */
    public static void clearLogFiles(ConsoleLogger consoleLogger) {
        try{
            new FileOutputStream(REPORT01_CSV).close();
            new FileOutputStream(TOP_CSV).close();
            new FileOutputStream(APPLICATION_LOG).close();
            new FileOutputStream(REPORT02_CSV).close();
        } catch (IOException e){
            consoleLogger.logError("Logfile reading error(application.log or report01.csv," +
                    " top.csv, or report02.csv", "");
        }
    }

    private void saveAllSpendingByCustomer(){
        FileLogger report01 = new FileLogger(REPORT01_CSV);
        paymentService.saveAllSpendingByCustomer(report01);
    }

    private void saveTopCustomerSpending(){
        FileLogger top = new FileLogger(TOP_CSV);
        paymentService.saveTopSpendingCustomersToFile(top, NUMBER_OF_TOP_CUSTOMERS);
    }

    private void saveWebshopIncomes(){
        FileLogger report02 = new FileLogger(REPORT02_CSV);
        paymentService.saveWebshopIncomesToFile(report02);
    }
}
