package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserRegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/userregister")
    public Result login(@RequestBody User user){
        log.info("员工注册: {}", user);
        try {
            User u = userService.register(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.success();
    }
}
