package org.example.controller;

import org.example.pojo.PurchaseHistory;
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
    public ResponseEntity<List<PurchaseHistory>> getAllPurchaseHistories() {
        return new ResponseEntity<>(purchaseHistoryService.getAllPurchaseHistories(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseHistory> getPurchaseHistoryById(@PathVariable Integer id) {
        PurchaseHistory purchaseHistory = purchaseHistoryService.getPurchaseHistoryById(id);
        if (purchaseHistory != null) {
            return new ResponseEntity<>(purchaseHistory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PurchaseHistory> createPurchaseHistory(@RequestBody PurchaseHistory purchaseHistory) {
        purchaseHistoryService.addPurchaseHistory(purchaseHistory);
        return new ResponseEntity<>(purchaseHistory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseHistory> updatePurchaseHistory(@PathVariable Integer id, @RequestBody PurchaseHistory purchaseHistory) {
        PurchaseHistory existingPurchaseHistory = purchaseHistoryService.getPurchaseHistoryById(id);
        if (existingPurchaseHistory != null) {
            purchaseHistory.setPurchaseId(id);
            purchaseHistoryService.updatePurchaseHistory(purchaseHistory);
            return new ResponseEntity<>(purchaseHistory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseHistory(@PathVariable Integer id) {
        PurchaseHistory existingPurchaseHistory = purchaseHistoryService.getPurchaseHistoryById(id);
        if (existingPurchaseHistory != null) {
            purchaseHistoryService.deletePurchaseHistory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

