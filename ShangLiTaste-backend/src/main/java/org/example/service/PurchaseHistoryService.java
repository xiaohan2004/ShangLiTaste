package org.example.service;

import org.example.pojo.PurchaseHistory;
import java.util.List;

public interface PurchaseHistoryService {
    List<PurchaseHistory> getAllPurchaseHistories();
    PurchaseHistory getPurchaseHistoryById(Integer id);
    void addPurchaseHistory(PurchaseHistory purchaseHistory);
    void updatePurchaseHistory(PurchaseHistory purchaseHistory);
    void deletePurchaseHistory(Integer id);
    List<PurchaseHistory> getPurchaseHistoryByCustomerId(Integer customerId);
}

