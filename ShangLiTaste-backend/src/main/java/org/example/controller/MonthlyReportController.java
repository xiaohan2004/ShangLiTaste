package org.example.controller;

import org.example.pojo.MonthlyReport;
import org.example.service.MonthlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-reports")
public class MonthlyReportController {

    @Autowired
    private MonthlyReportService monthlyReportService;

    @GetMapping
    public ResponseEntity<List<MonthlyReport>> getAllMonthlyReports() {
        return new ResponseEntity<>(monthlyReportService.getAllMonthlyReports(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonthlyReport> getMonthlyReportById(@PathVariable Integer id) {
        MonthlyReport monthlyReport = monthlyReportService.getMonthlyReportById(id);
        if (monthlyReport != null) {
            return new ResponseEntity<>(monthlyReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/month/{month}/year/{year}")
    public ResponseEntity<MonthlyReport> getMonthlyReportByMonthAndYear(@PathVariable Integer month, @PathVariable Integer year) {
        MonthlyReport monthlyReport = monthlyReportService.getMonthlyReportByMonthAndYear(month, year);
        if (monthlyReport != null) {
            return new ResponseEntity<>(monthlyReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MonthlyReport> createMonthlyReport(@RequestBody MonthlyReport monthlyReport) {
        monthlyReportService.addMonthlyReport(monthlyReport);
        return new ResponseEntity<>(monthlyReport, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthlyReport> updateMonthlyReport(@PathVariable Integer id, @RequestBody MonthlyReport monthlyReport) {
        MonthlyReport existingMonthlyReport = monthlyReportService.getMonthlyReportById(id);
        if (existingMonthlyReport != null) {
            monthlyReport.setReportId(id);
            monthlyReportService.updateMonthlyReport(monthlyReport);
            return new ResponseEntity<>(monthlyReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonthlyReport(@PathVariable Integer id) {
        MonthlyReport existingMonthlyReport = monthlyReportService.getMonthlyReportById(id);
        if (existingMonthlyReport != null) {
            monthlyReportService.deleteMonthlyReport(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

