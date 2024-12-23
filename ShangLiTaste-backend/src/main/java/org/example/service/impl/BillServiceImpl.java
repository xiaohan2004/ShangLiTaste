package org.example.service.impl;

import org.example.mapper.BillMapper;
import org.example.pojo.Bill;
import org.example.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Override
    public List<Bill> getAllBills() {
        return billMapper.getAllBills();
    }

    @Override
    public Bill getBillById(Integer id) {
        return billMapper.getBillById(id);
    }

    @Override
    public void addBill(Bill bill) {
        billMapper.insertBill(bill);
    }

    @Override
    public void updateBill(Bill bill) {
        billMapper.updateBill(bill);
    }

    @Override
    public void deleteBill(Integer id) {
        billMapper.deleteBill(id);
    }
}

