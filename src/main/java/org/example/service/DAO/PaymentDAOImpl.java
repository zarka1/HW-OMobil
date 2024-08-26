package org.example.service;

import org.example.model.Customer;
import org.example.model.Payment;
import org.example.model.PaymentMethod;
import org.example.model.WebshopId;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaymentDAOImpl implements PaymentDAO{

    private List<Payment> payments;
    private String filePath;
    private Logger logger;
    private CustomerDAO customerDAO;

    public PaymentDAOImpl(String filePath, Logger logger, CustomerDAO customerDAO) {
        this.filePath = filePath;
        this.logger = logger;
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
                String data = myReader.nextLine();
                savePaymentInMem(data, payments);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return payments;
    }

    private void savePaymentInMem(String paymentData, List<Payment> payments){
        String[] paymentArray = paymentData.split(";");
        if(WebshopId.checkIfExist(paymentArray[0]) == false ){
            logger.logError("Webshop Id not correct.", paymentData);
        } else if(!customerDAO.checkIfExist(paymentArray[0] + "_" + paymentArray[1])){//check if customer valid
            logger.logError("Customer does not exist", paymentData);
        } else if((paymentArray[2] == "card" &&//check if transfer number and card number are exist and just the required on exists
                (paymentArray[4].length() != 0  || paymentArray[5].length() == 0)) ||
                (paymentArray[2] == "transfer") &&
                        (paymentArray[4].length() == 0 || paymentArray[5].length() != 0)){
            logger.logError("Cardnumber or transfernumber not correct.", paymentData);
        } else if(convertToDouble(paymentArray[3]) == null){
            logger.logError("Amount not correct.", paymentData);
        } else if(parseToDate(paymentArray[6]) == null){
            logger.logError("Date is not correct.", paymentData);
        }
        else {
            createPayment(paymentData, payments, paymentArray);
        }
    }

    private void createPayment(String paymentData, List<Payment> payments, String[] paymentArray) {
        Payment payment = new Payment(WebshopId.findByName(paymentArray[0]),
                paymentArray[1],
                PaymentMethod.findByName(paymentArray[2]),
                Double.parseDouble(paymentArray[3]),
                        null,
                        null,
                                parseToDate(paymentArray[6].replaceAll("[:,-]", "."))
                );
        if (PaymentMethod.findByName(paymentArray[2]) == PaymentMethod.CARD) {
            if (convertToDouble(paymentArray[5]) == null){
                logger.logError("Card number not correct.", paymentData);
                return;
            }
            payment.setCardNumber(convertToDouble(paymentArray[5]));
        }
        else {
            if (convertToDouble(paymentArray[4]) == null){
                logger.logError("Transfer number not correct.", paymentData);
                return;
            }
            payment.setAccountNumber(convertToDouble(paymentArray[4]));
        }
        payments.add(payment);
    }

    private Double convertToDouble(String number){
        try {
            if (number == "") return null;
            number = (number.replaceAll("[+]", ""));
            return Double.parseDouble(number.replaceAll(",", "."));

        } catch (NumberFormatException e) {
            return null;
        }
    }

    private LocalDate parseToDate(String dateString){
        dateString = dateString.replaceAll("[:,.]", "-");
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(dateString, formatter);
            return date;
        } catch (DateTimeParseException e){
            return null;
        }
    }

    public List<Payment> getPayments(){
        return new ArrayList<Payment>(payments);
    }
}
