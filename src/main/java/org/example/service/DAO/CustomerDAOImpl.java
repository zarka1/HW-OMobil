package org.example.service.DAO;

import org.example.model.Customer;
import org.example.model.WebshopId;
import org.example.service.Logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    private List<Customer> customers;
    private String filePath;
    private Logger logger;

    public CustomerDAOImpl(String filePath, Logger logger) {
        this.filePath = filePath;
        this.logger = logger;
        this.customers = readCustomers();
    }

    @Override
    public List<Customer> readCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                saveCustomerInMem(data, customers);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public boolean checkIfExist( String customerId) {
        for(Customer customer : customers){
            if(customer.identified( customerId)){
                return true;
            }
        }
        return false;
    }

    private void saveCustomerInMem(String customerData, List<Customer> customers){
        String[] customerArray = customerData.split(";");
        if(WebshopId.checkIfExist(customerArray[0]) == false ){
            logger.logError("Webshop Id not correct.", customerData);
        } else if(!checkAddress(customerArray[3])){
            logger.logError("Address is not correct.", customerData);
        }
        else {
            Customer customer = new Customer(WebshopId.findByName(customerArray[0]),
                    customerArray[1],
                    customerArray[2],
                    customerArray[3]);
            customers.add(customer);
        }
    }

    private boolean checkAddress(String address){
        String[] addressArray = address.split(" ");
        if(addressArray[1].length() != 4 || addressArray.length != 5)
            return false;
        try{
            Integer.parseInt(addressArray[1]);
            Integer.parseInt(addressArray[4]);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
