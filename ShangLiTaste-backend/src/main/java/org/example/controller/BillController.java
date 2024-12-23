package org.example.controller;

import org.example.pojo.Bill;
import org.example.pojo.Result;
import org.example.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping
    public Result getAllBills() {
        return Result.success(billService.getAllBills());
    }

    @GetMapping("/{id}")
    public Result getBillById(@PathVariable Integer id) {
        Bill bill = billService.getBillById(id);
        if (bill != null) {
            return Result.success(bill);
        } else {
            return Result.error("Not Found");
        }
    }

    @PostMapping
    public Result createBill(@RequestBody Bill bill) {
        billService.addBill(bill);
        return Result.success(bill);
    }

    @PutMapping("/{id}")
    public Result updateBill(@PathVariable Integer id, @RequestBody Bill bill) {
        Bill existingBill = billService.getBillById(id);
        if (existingBill != null) {
            bill.setBillId(id);
            billService.updateBill(bill);
            return Result.success(bill);
        } else {
            return Result.error("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteBill(@PathVariable Integer id) {
        Bill existingBill = billService.getBillById(id);
        if (existingBill != null) {
            billService.deleteBill(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }
}

