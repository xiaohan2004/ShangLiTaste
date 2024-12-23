package org.example.service.impl;

import org.example.mapper.AnnualReportMapper;
import org.example.pojo.AnnualReport;
import org.example.service.AnnualReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnualReportServiceImpl implements AnnualReportService {

    @Autowired
    private AnnualReportMapper annualReportMapper;

    @Override
    public List<AnnualReport> getAllAnnualReports() {
        return annualReportMapper.getAllAnnualReports();
    }

    @Override
    public AnnualReport getAnnualReportById(Integer id) {
        return annualReportMapper.getAnnualReportById(id);
    }

    @Override
    public AnnualReport getAnnualReportByYear(Integer year) {
        return annualReportMapper.getAnnualReportByYear(year);
    }

    @Override
    public void addAnnualReport(AnnualReport annualReport) {
        annualReportMapper.insertAnnualReport(annualReport);
    }

    @Override
    public void updateAnnualReport(AnnualReport annualReport) {
        annualReportMapper.updateAnnualReport(annualReport);
    }

    @Override
    public void deleteAnnualReport(Integer id) {
        annualReportMapper.deleteAnnualReport(id);
    }
}

