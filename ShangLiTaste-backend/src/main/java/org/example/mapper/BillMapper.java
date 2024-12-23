package org.example.mapper;

import org.example.pojo.Bill;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BillMapper {
    @Select("SELECT * FROM bills")
    List<Bill> getAllBills();

    @Select("SELECT * FROM bills WHERE bill_id = #{billId}")
    Bill getBillById(Integer billId);

    @Insert("INSERT INTO bills(order_id, total_amount, paid_amount, change_amount, payment_status, payment_method) VALUES(#{orderId}, #{totalAmount}, #{paidAmount}, #{changeAmount}, #{paymentStatus}, #{paymentMethod})")
    @Options(useGeneratedKeys = true, keyProperty = "billId")
    void insertBill(Bill bill);

    @Update("UPDATE bills SET order_id = #{orderId}, total_amount = #{totalAmount}, paid_amount = #{paidAmount}, change_amount = #{changeAmount}, payment_status = #{paymentStatus}, payment_method = #{paymentMethod} WHERE bill_id = #{billId}")
    void updateBill(Bill bill);

    @Delete("DELETE FROM bills WHERE bill_id = #{billId}")
    void deleteBill(Integer billId);
}

