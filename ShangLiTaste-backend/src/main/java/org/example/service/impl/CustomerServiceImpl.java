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
        customerMapper.updateCustomerTotalSpentById(id);
        return customerMapper.getCustomerById(id);
    }

    @Override
    public void addCustomer(Customer customer) {
        //先检查用户名是否已存在
        Customer existingCustomer = customerMapper.getCustomerByName(customer.getName());
        if (existingCustomer != null) {
            throw new RuntimeException("Username already exists");
        }
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

    @Override
    public Customer login(Customer customer) {
        return customerMapper.getCustomerByNameAndPassword(customer);
    }

    @Override
    public Customer register(Customer customer) {
        //先检查用户名是否已存在
        Customer existingCustomer = customerMapper.getCustomerByName(customer.getName());
        if (existingCustomer != null) {
            throw new RuntimeException("Username already exists");
        }
        customerMapper.insertCustomer(customer);
        return customer;
    }
}

