package org.example.service.DAO;

import org.example.model.Customer;
import org.example.service.Logger.ConsoleLogger;
import org.example.service.Logger.FileLogger;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    private final List<Customer> customers;
    private final java.lang.String filePath;
    private final FileLogger fileLogger;
    private final ConsoleLogger consoleLogger;

    public CustomerDAOImpl(java.lang.String filePath, FileLogger fileLogger, ConsoleLogger consoleLogger) {
        this.filePath = filePath;
        this.fileLogger = fileLogger;
        this.consoleLogger = consoleLogger;
        this.customers = readCustomers();
    }

    private List<Customer> readCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                java.lang.String data = myReader.nextLine();
                saveCustomerInMem(data, customers);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            consoleLogger.logInfo("An error occurred.");
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public boolean checkIfExist( java.lang.String customerId) {
        for(Customer customer : customers){
            if(customer.hasCustomerId( customerId)){
                return true;
            }
        }
        return false;
    }

    private void saveCustomerInMem(java.lang.String customerData, List<Customer> customers){
        java.lang.String[] customerArray = customerData.split(";");

       if(!checkAddress(customerArray[3])){
            fileLogger.logError("Address is not correct.", customerData);
        }
        else {
            Customer customer = new Customer(customerArray[0],
                    customerArray[1],
                    customerArray[2],
                    customerArray[3]);
            customers.add(customer);
        }
    }

    private boolean checkAddress(java.lang.String address){
        java.lang.String[] addressArray = address.split(" ");
        if(addressArray[1].length() != 4 || addressArray.length != 5)
            return false;
        try{
            Integer.parseInt(addressArray[1]);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
