package org.example.service.impl;

import org.example.mapper.CustomerMapper;
import org.example.pojo.Customer;
import org.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<Customer> getAllCustomers() {
        return customerMapper.getAllCustomers();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerMapper.getCustomerById(id);
    }

    @Override
    public void addCustomer(Customer customer) {
        customerMapper.insertCustomer(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerMapper.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerMapper.deleteCustomer(id);
    }
}

