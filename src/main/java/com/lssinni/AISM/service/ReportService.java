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
        this.iamClient = iamClient;     // Spring에서 주입된 IamClient 사용
    }

    // Credential Report 생성 메서드
    public List<IamUser> generateCredentialReport() throws InterruptedException {
        try {
            // Credential Report 생성 요청
            GenerateCredentialReportRequest generateRequest = GenerateCredentialReportRequest.builder().build();
            GenerateCredentialReportResponse generateResponse = iamClient.generateCredentialReport(generateRequest);

            // Credential Report가 준비될 때까지 대기
            System.out.println("Credential report 생성 요청 완료");

            // 보고서가 준비될 때까지 대기
            GetCredentialReportRequest getRequest = GetCredentialReportRequest.builder().build();
            GetCredentialReportResponse getResponse;

            // 보고서가 준비될 때까지 대기
            do {
                getResponse = iamClient.getCredentialReport(getRequest);
                if (getResponse.content() == null) {
                    System.out.println("보고서 준비 중...");
                    Thread.sleep(2000); // 2초 대기 후 다시 시도
                }
            } while (getResponse.content() == null);

            // SdkBytes로 반환된 보고서 내용
            SdkBytes reportContent = getResponse.content();

            byte[] contents = reportContent.asByteArray();    // SdkBytes에서 byte[] 배열로 변환

            // report에서 iamUser 리스트 반환
            Report report = new Report(contents);
            List<IamUser> iamUsers = report.parseCsv();

            return iamUsers;

        } catch (SdkException e) {
            e.printStackTrace();
            throw new RuntimeException("AWS IAM API 호출 중 오류 발생");
        }
    }


}
