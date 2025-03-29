package com.lssinni.AISM.service;

import java.util.List;

import com.lssinni.AISM.domain.IamUser;
import com.lssinni.AISM.domain.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.GenerateCredentialReportRequest;
import software.amazon.awssdk.services.iam.model.GenerateCredentialReportResponse;
import software.amazon.awssdk.services.iam.model.GetCredentialReportRequest;
import software.amazon.awssdk.services.iam.model.GetCredentialReportResponse;

@Service
public class ReportService {

    private final IamClient iamClient;

    @Autowired
    public ReportService(IamClient iamClient) {
        this.iamClient = iamClient;     // Bean에 주입된 IamClient 사용
    }


    public List<IamUser> generateCredentialReport() throws InterruptedException {

        // Credential Report 생성 요청
        GenerateCredentialReportRequest generateRequest = GenerateCredentialReportRequest.builder().build();
        GenerateCredentialReportResponse generateResponse = iamClient.generateCredentialReport(generateRequest);

        // 보고서 요청
        GetCredentialReportRequest getRequest = GetCredentialReportRequest.builder().build();
        GetCredentialReportResponse getResponse;

        do {        // 보고서가 준비될 때까지 대기 및 반복 요청
            getResponse = iamClient.getCredentialReport(getRequest);
            if (getResponse.content() == null) {
                Thread.sleep(2000); // 2초 대기 후 다시 시도
            }
        } while (getResponse.content() == null);

        SdkBytes reportContent = getResponse.content();         // SdkBytes로 report 내용 반환

        // iamUser 리스트로 변환
        Report report = new Report(reportContent.asByteArray());        // SdkBytes에서 byte[] 배열로 변환해서 매개변수로 전달
        List<IamUser> iamUsers = report.parseCsv();

        return iamUsers;
    }
}
