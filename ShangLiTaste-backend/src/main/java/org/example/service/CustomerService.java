package org.example.service;

import org.example.pojo.Customer;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Integer id);
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(Integer id);
}

