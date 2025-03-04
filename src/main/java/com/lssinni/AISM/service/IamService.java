package com.lssinni.AISM.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class IamService {

    private final IamClient iamClient;

    @Autowired
    public IamService(IamClient iamClient) {
        this.iamClient = iamClient;     // Spring에서 주입된 IamClient 사용
    }

    // Credential Report 생성 메서드
    public String generateCredentialReport() throws InterruptedException {
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

            // SdkBytes에서 byte[] 배열로 변환
            byte[] decodedBytes = reportContent.asByteArray();

            // CSV 데이터 읽기
            return parseCsv(decodedBytes);  // 디코딩된 CSV 데이터를 파싱하여 반환

        } catch (SdkException e) {
            e.printStackTrace();
            throw new RuntimeException("AWS IAM API 호출 중 오류 발생");
        }
    }

    // CSV 데이터를 파싱하여 출력
    private String parseCsv(byte[] decodedBytes) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(decodedBytes)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "CSV 파일을 읽는 중 오류 발생";
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
