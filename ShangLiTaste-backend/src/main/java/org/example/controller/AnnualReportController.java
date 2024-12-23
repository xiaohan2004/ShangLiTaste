package org.example.controller;

import org.example.pojo.AnnualReport;
import org.example.service.AnnualReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annual-reports")
public class AnnualReportController {

    @Autowired
    private AnnualReportService annualReportService;

    @GetMapping
    public ResponseEntity<List<AnnualReport>> getAllAnnualReports() {
        return new ResponseEntity<>(annualReportService.getAllAnnualReports(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnualReport> getAnnualReportById(@PathVariable Integer id) {
        AnnualReport annualReport = annualReportService.getAnnualReportById(id);
        if (annualReport != null) {
            return new ResponseEntity<>(annualReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<AnnualReport> getAnnualReportByYear(@PathVariable Integer year) {
        AnnualReport annualReport = annualReportService.getAnnualReportByYear(year);
        if (annualReport != null) {
            return new ResponseEntity<>(annualReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AnnualReport> createAnnualReport(@RequestBody AnnualReport annualReport) {
        annualReportService.addAnnualReport(annualReport);
        return new ResponseEntity<>(annualReport, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnualReport> updateAnnualReport(@PathVariable Integer id, @RequestBody AnnualReport annualReport) {
        AnnualReport existingAnnualReport = annualReportService.getAnnualReportById(id);
        if (existingAnnualReport != null) {
            annualReport.setReportId(id);
            annualReportService.updateAnnualReport(annualReport);
            return new ResponseEntity<>(annualReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnualReport(@PathVariable Integer id) {
        AnnualReport existingAnnualReport = annualReportService.getAnnualReportById(id);
        if (existingAnnualReport != null) {
            annualReportService.deleteAnnualReport(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

