package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Customer;
import org.example.pojo.Result;
import org.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CustomerRegisterController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/customerregister")
    public Result login(@RequestBody Customer customer) {
        log.info("顾客注册: {}", customer);
        try {
            Customer c = customerService.register(customer);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.success();
    }
}
