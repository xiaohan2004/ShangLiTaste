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

    @Insert("INSERT INTO customers(name, phone, email, address, registration_date, birthday, total_spent) VALUES(#{name}, #{phone}, #{email}, #{address}, #{registrationDate}, #{birthday}, #{totalSpent})")
    @Options(useGeneratedKeys = true, keyProperty = "customerId")
    void insertCustomer(Customer customer);

    @Update("UPDATE customers SET name = #{name}, phone = #{phone}, email = #{email}, address = #{address}, registration_date = #{registrationDate}, birthday = #{birthday}, total_spent = #{totalSpent} WHERE customer_id = #{customerId}")
    void updateCustomer(Customer customer);

    @Delete("DELETE FROM customers WHERE customer_id = #{customerId}")
    void deleteCustomer(Integer customerId);
}

