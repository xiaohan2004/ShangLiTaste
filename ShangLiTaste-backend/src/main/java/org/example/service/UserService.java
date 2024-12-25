package org.example.service;

import org.example.pojo.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Integer id);
    User getUserByUsername(String username);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Integer id);
    User login(User user);
    User register(User user);
}

