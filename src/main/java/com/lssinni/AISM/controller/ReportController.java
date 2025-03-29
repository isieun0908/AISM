package com.lssinni.AISM.controller;

import com.lssinni.AISM.domain.IamUser;
import com.lssinni.AISM.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/generate")
    public List<IamUser> getReport() {
        try {
            return reportService.generateCredentialReport();
        } catch (InterruptedException e) {
            throw new RuntimeException("report 생성에 실패했습니다.");
        }
    }
}
