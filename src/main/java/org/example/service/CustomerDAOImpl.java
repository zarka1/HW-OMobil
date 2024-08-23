package org.example.service;

import org.example.model.Customer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    private List<Customer> customers;
    private String filePath;

    public CustomerDAOImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Customer> readCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return customers;
    }

    private void saveCustomerDataInMem (String customerData){
        String[] customerArray = customerData.split(";");

    }
}
