package org.example.controller;

import org.example.pojo.Customer;
import org.example.pojo.Result;
import org.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Result getAllCustomers() {
        return Result.success(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public Result getCustomerById(@PathVariable Integer id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            return Result.success(customer);
        } else {
            return Result.error("Not Found");
        }
    }

    @PostMapping
    public Result createCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return Result.success(customer);
    }

    @PutMapping("/{id}")
    public Result updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer != null) {
            customer.setCustomerId(id);
            customerService.updateCustomer(customer);
            return Result.success(customer);
        } else {
            return Result.error("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteCustomer(@PathVariable Integer id) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer != null) {
            customerService.deleteCustomer(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }
}

