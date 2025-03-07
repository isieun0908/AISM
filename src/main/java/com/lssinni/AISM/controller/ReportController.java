package com.lssinni.AISM.controller;

import com.lssinni.AISM.domain.IamUser;
import com.lssinni.AISM.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    private final ReportService iamService;

    @Autowired
    public ReportController(ReportService iamService) {
        this.iamService = iamService;
    }

    // HTTP GET 요청으로 보고서를 생성하고 반환하는 엔드포인트
    @GetMapping("/generate")
    public List<IamUser> generateCredentialReport() {
        try {
            // IAMService를 통해 Credential Report 생성
            return iamService.generateCredentialReport();
        } catch (InterruptedException e) {
            //return "Credential Report 생성 중 오류가 발생했습니다: " + e.getMessage();
        }
        return null;
    }
}
