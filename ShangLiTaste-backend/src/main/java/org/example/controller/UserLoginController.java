package org.example.controller;

import org.example.pojo.User;
import org.example.pojo.Result;
import org.example.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserLoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/userlogin")
    public Result login(@RequestBody User user){
        log.info("员工登录: {}", user);
        User u = userService.login(user);

        //登录成功,生成令牌,下发令牌
        if (u != null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", u.getUserId());
            claims.put("username", u.getUsername());
            claims.put("role", u.getRole());
            claims.put("status", u.getStatus());

            String jwt = JwtUtils.generateJwt(claims); //jwt包含了当前登录的员工信息
            return Result.success(jwt);
        }

        //登录失败, 返回错误信息
        return Result.error("用户名或密码错误");
    }

}
