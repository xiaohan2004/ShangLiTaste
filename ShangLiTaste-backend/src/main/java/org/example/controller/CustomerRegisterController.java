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
    public Result login(@RequestBody Customer customer){
        log.info("顾客注册: {}", customer);
        Customer c = customerService.register(customer);

        //注册成功,返回成功信息
        if (c != null){
            return Result.success();
        }

        //注册失败, 返回错误信息
        return Result.error("用户名已存在");
    }
}
