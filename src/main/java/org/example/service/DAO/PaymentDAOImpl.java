package org.example.service.DAO;

import org.example.model.Customer;
import org.example.model.Payment;
import org.example.model.PaymentMethod;
import org.example.model.WebshopIncome;
import org.example.service.Logger.ConsoleLogger;
import org.example.service.Logger.FileLogger;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaymentDAOImpl implements PaymentDAO {

    private final List<Payment> payments;
    private final java.lang.String filePath;
    private final FileLogger fileLogger;
    private final ConsoleLogger consoleLogger;
    private final CustomerDAO customerDAO;


    public PaymentDAOImpl(java.lang.String filePath, FileLogger fileLogger,
                          ConsoleLogger consoleLogger, CustomerDAO customerDAO) {
        this.filePath = filePath;
        this.fileLogger = fileLogger;
        this.consoleLogger = consoleLogger;
        this.customerDAO = customerDAO;
        this.payments = readPayments();
    }

    @Override
    public List<Payment> readPayments() {
        List<Payment> payments = new ArrayList<>();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                java.lang.String data = myReader.nextLine();
                savePaymentInMem(data, payments);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            consoleLogger.logInfo("An error occurred.");
            e.printStackTrace();
        }
        return payments;
    }

    private void savePaymentInMem(java.lang.String paymentData, List<Payment> payments){
        java.lang.String[] paymentArray = paymentData.split(";");
        if(!customerDAO.checkIfExist(paymentArray[0] + "_" + paymentArray[1])){
            fileLogger.logError("Customer does not exist", paymentData);
        } else if((paymentArray[2].equals("card") &&
                (paymentArray[4].length() != 0  || paymentArray[5].length() == 0)) ||
                (paymentArray[2].equals("transfer") &&
                        (paymentArray[4].length() == 0 || paymentArray[5].length() != 0))){
            fileLogger.logError("Card number or transfer number not correct.", paymentData);
        } else if(convertToDouble(paymentArray[3]) == null){
            fileLogger.logError("Amount not correct.", paymentData);
        } else if(parseToDate(paymentArray[6]) == null){
            fileLogger.logError("Date is not correct.", paymentData);
        }
        else {
            createPayment(paymentData, payments, paymentArray);
        }
    }

    private void createPayment(java.lang.String paymentData, List<Payment> payments, java.lang.String[] paymentArray) {
        Payment payment = new Payment(paymentArray[0],
                paymentArray[1],
                PaymentMethod.findByName(paymentArray[2]),
                Double.parseDouble(paymentArray[3]),
                        null,
                        null,
                                parseToDate(paymentArray[6].replaceAll("[:,-]", "."))
                );
        if (PaymentMethod.findByName(paymentArray[2]) == PaymentMethod.CARD) {
            if (convertToDouble(paymentArray[5]) == null){
                fileLogger.logError("Card number not correct.", paymentData);
                return;
            }
            payment.setCardNumber(convertToDouble(paymentArray[5]));
        }
        else {
            if (convertToDouble(paymentArray[4]) == null){
                fileLogger.logError("Transfer number not correct.", paymentData);
                return;
            }
            payment.setAccountNumber(convertToDouble(paymentArray[4]));
        }
        payments.add(payment);
    }

    private Double convertToDouble(java.lang.String number){
        try {
            if (number.equals("")) return null;
            number = (number.replaceAll("[+]", ""));
            return Double.parseDouble(number.replaceAll(",", "."));

        } catch (NumberFormatException e) {
            return null;
        }
    }

    private LocalDate parseToDate(java.lang.String dateString){
        dateString = dateString.replaceAll("[:,.]", "-");
        try{
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    .toFormatter();
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e){
            return null;
        }
    }

    public List<Payment> getPayments(){
        return new ArrayList<Payment>(payments);
    }

    public void saveWebshopIncomesToFile(FileLogger fileLogger, List<WebshopIncome> webshopIncomes){
        for(WebshopIncome webshopIncome : webshopIncomes){
            fileLogger.logInfo(webshopIncome.getWebshopId() + ";"
                    + webshopIncome.getCardPayments() + ";"
                    + webshopIncome.getTransferPayments());
        }
    }

    public void saveAllCustomersSpendingToFile(FileLogger fileLogger, List<Customer> customers){
        for (Customer customer : customers){
            fileLogger.logInfo(customer.getCustomerName() +";" +
                    customer.getCustomerAddress() +";" +
                    customer.getTotalSpending());
        }
    }
}
