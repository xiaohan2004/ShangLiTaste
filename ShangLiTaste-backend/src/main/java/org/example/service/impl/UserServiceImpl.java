package org.example.service.impl;

import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public void addUser(User user) {
        //先检查用户名是否已存在
        User existingUser = userMapper.getUserByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("Username already exists");
        }
        userMapper.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    @Override
    public User login(User user) {
        return userMapper.getUserByUsernameAndPassword(user);
    }

    @Override
    public User register(User user) {
        //先检查用户名是否已存在
        User existingUser = userMapper.getUserByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("Username already exists");
        }
        userMapper.insertUser(user);
        return user;
    }
}

