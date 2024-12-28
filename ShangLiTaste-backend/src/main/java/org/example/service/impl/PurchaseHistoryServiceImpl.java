package org.example.service.impl;

import org.example.mapper.PurchaseHistoryMapper;
import org.example.pojo.PurchaseHistory;
import org.example.service.PurchaseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    @Autowired
    private PurchaseHistoryMapper purchaseHistoryMapper;

    @Override
    public List<PurchaseHistory> getAllPurchaseHistories() {
        return purchaseHistoryMapper.getAllPurchaseHistories();
    }

    @Override
    public PurchaseHistory getPurchaseHistoryById(Integer id) {
        return purchaseHistoryMapper.getPurchaseHistoryById(id);
    }

    @Override
    public void addPurchaseHistory(PurchaseHistory purchaseHistory) {
        purchaseHistoryMapper.insertPurchaseHistory(purchaseHistory);
    }

    @Override
    public void updatePurchaseHistory(PurchaseHistory purchaseHistory) {
        purchaseHistoryMapper.updatePurchaseHistory(purchaseHistory);
    }

    @Override
    public void deletePurchaseHistory(Integer id) {
        purchaseHistoryMapper.deletePurchaseHistory(id);
    }

    @Override
    public List<PurchaseHistory> getPurchaseHistoryByCustomerId(Integer customerId) {
        return purchaseHistoryMapper.getPurchaseHistoryByCustomerId(customerId);
    }

    @Override
    public PurchaseHistory getPurchaseHistoryByOrderId(Integer id) {
        return purchaseHistoryMapper.getPurchaseHistoryByOrderId(id);
    }

    @Override
    public void addBillByOrder(Integer id, Integer customerId) {
        purchaseHistoryMapper.insertPurchaseHistoryByOrder(id, customerId);
    }
}

