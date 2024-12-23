package org.example.mapper;

import org.example.pojo.PurchaseHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PurchaseHistoryMapper {
    @Select("SELECT * FROM purchase_history")
    List<PurchaseHistory> getAllPurchaseHistories();

    @Select("SELECT * FROM purchase_history WHERE purchase_id = #{purchaseId}")
    PurchaseHistory getPurchaseHistoryById(Integer purchaseId);

    @Insert("INSERT INTO purchase_history(customer_id, order_id, purchase_date, total_amount, payment_method) VALUES(#{customerId}, #{orderId}, #{purchaseDate}, #{totalAmount}, #{paymentMethod})")
    @Options(useGeneratedKeys = true, keyProperty = "purchaseId")
    void insertPurchaseHistory(PurchaseHistory purchaseHistory);

    @Update("UPDATE purchase_history SET customer_id = #{customerId}, order_id = #{orderId}, purchase_date = #{purchaseDate}, total_amount = #{totalAmount}, payment_method = #{paymentMethod} WHERE purchase_id = #{purchaseId}")
    void updatePurchaseHistory(PurchaseHistory purchaseHistory);

    @Delete("DELETE FROM purchase_history WHERE purchase_id = #{purchaseId}")
    void deletePurchaseHistory(Integer purchaseId);
}

