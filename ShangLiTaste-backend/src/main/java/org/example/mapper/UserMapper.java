package org.example.mapper;

import org.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users")
    List<User> getAllUsers();

    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    User getUserById(Integer userId);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User getUserByUsername(String username);

    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{password}")
    User getUserByUsernameAndPassword(User user);

    @Insert("INSERT INTO users(username, password, role, status) VALUES(#{username}, #{password}, #{role}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void insertUser(User user);

    @Update("UPDATE users SET username = #{username}, password = #{password}, role = #{role}, status = #{status} WHERE user_id = #{userId}")
    void updateUser(User user);

    @Delete("DELETE FROM users WHERE user_id = #{userId}")
    void deleteUser(Integer userId);
}