package org.example.controller;

import org.example.pojo.PurchaseHistory;
import org.example.pojo.Result;
import org.example.service.PurchaseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-history")
public class PurchaseHistoryController {

    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    @GetMapping
    public Result getAllPurchaseHistories() {
        return Result.success(purchaseHistoryService.getAllPurchaseHistories());
    }

    @GetMapping("/{id}")
    public Result getPurchaseHistoryById(@PathVariable Integer id) {
        PurchaseHistory purchaseHistory = purchaseHistoryService.getPurchaseHistoryById(id);
        if (purchaseHistory != null) {
            return Result.success(purchaseHistory);
        } else {
            return Result.error("Not Found");
        }
    }

    @GetMapping("/customer/{customerId}")
    public Result getPurchaseHistoryByCustomerId(@PathVariable Integer customerId) {
        List<PurchaseHistory> purchaseHistories = purchaseHistoryService.getPurchaseHistoryByCustomerId(customerId);
        return Result.success(purchaseHistories);
    }

    @PostMapping
    public Result createPurchaseHistory(@RequestBody PurchaseHistory purchaseHistory) {
        purchaseHistoryService.addPurchaseHistory(purchaseHistory);
        return Result.success(purchaseHistory);
    }

    @PutMapping("/{id}")
    public Result updatePurchaseHistory(@PathVariable Integer id, @RequestBody PurchaseHistory purchaseHistory) {
        PurchaseHistory existingPurchaseHistory = purchaseHistoryService.getPurchaseHistoryById(id);
        if (existingPurchaseHistory != null) {
            purchaseHistory.setPurchaseId(id);
            purchaseHistoryService.updatePurchaseHistory(purchaseHistory);
            return Result.success(purchaseHistory);
        } else {
            return Result.error("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public Result deletePurchaseHistory(@PathVariable Integer id) {
        PurchaseHistory existingPurchaseHistory = purchaseHistoryService.getPurchaseHistoryById(id);
        if (existingPurchaseHistory != null) {
            purchaseHistoryService.deletePurchaseHistory(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }
}

