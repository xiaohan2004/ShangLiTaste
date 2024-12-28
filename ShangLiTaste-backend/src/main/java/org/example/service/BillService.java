package org.example.service;

import org.example.pojo.Bill;
import java.util.List;

public interface BillService {
    List<Bill> getAllBills();
    Bill getBillById(Integer id);
    void addBill(Bill bill);
    void updateBill(Bill bill);
    void deleteBill(Integer id);
    Bill getBillByOrderId(Integer id);
    void addBillByOrder(Integer id);
}

