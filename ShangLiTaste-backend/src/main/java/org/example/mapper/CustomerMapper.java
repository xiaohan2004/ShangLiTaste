package org.example.mapper;

import org.example.pojo.Customer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerMapper {
    @Select("SELECT * FROM customers")
    List<Customer> getAllCustomers();

    @Select("SELECT * FROM customers WHERE customer_id = #{customerId}")
    Customer getCustomerById(Integer customerId);

    @Select("SELECT * FROM customers WHERE name = #{name} AND password = #{password}")
    Customer getCustomerByNameAndPassword(Customer customer);

    @Select("SELECT * FROM customers WHERE name = #{name}")
    Customer getCustomerByName(String name);

    @Insert("INSERT INTO customers(name, password, phone, email, address, registration_date, birthday, total_spent) VALUES(#{name}, #{password}, #{phone}, #{email}, #{address}, #{registrationDate}, #{birthday}, #{totalSpent})")
    @Options(useGeneratedKeys = true, keyProperty = "customerId")
    void insertCustomer(Customer customer);

    @Update("UPDATE customers SET name = #{name}, phone = #{phone}, email = #{email}, address = #{address}, registration_date = #{registrationDate}, birthday = #{birthday}, total_spent = #{totalSpent} WHERE customer_id = #{customerId}")
    void updateCustomer(Customer customer);

    @Delete("DELETE FROM customers WHERE customer_id = #{customerId}")
    void deleteCustomer(Integer customerId);
}

