package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Customer;
import org.example.pojo.Result;
import org.example.service.CustomerService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class CustomerLoginController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/customerlogin")
    public Result login(@RequestBody Customer customer){
        log.info("顾客登录: {}", customer);
        Customer c = customerService.login(customer);

        //登录成功,生成令牌,下发令牌
        if (c != null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("customerId", c.getCustomerId());
            claims.put("name", c.getName());

            String jwt = JwtUtils.generateJwt(claims); //jwt包含了当前登录的员工信息
            return Result.success(jwt);
        }

        //登录失败, 返回错误信息
        return Result.error("用户名或密码错误");
    }
}
