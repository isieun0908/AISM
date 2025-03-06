package com.lssinni.AISM.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.lssinni.AISM.domain.IamUser;
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

            // SdkBytes에서 byte[] 배열로 변환
            byte[] decodedBytes = reportContent.asByteArray();

            // byte에서 string으로 변환
            String reportString = convertByteToString(decodedBytes);

            // CSV 데이터 읽기
            List<IamUser> iamUsers = parseCsv(reportString);    // 디코딩된 CSV 데이터를 파싱하여 반환

            return iamUsers;

        } catch (SdkException e) {
            e.printStackTrace();
            throw new RuntimeException("AWS IAM API 호출 중 오류 발생");
        }
    }

    /**
     * convertByteToString
     * - byte 배열을 문자열(String)으로 변환하여 반환
     * @param decodedBytes - byte 배열
     * @return string
     */
    protected String convertByteToString(byte[] decodedBytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : decodedBytes) {
            sb.append((char) b);
        }

        return sb.toString();
    }

    /**
     * parseCsv
     * - csv내용이 포함된 string을 IamUser 객체 리스트로 반환
     * @param reportString
     * @return List<IamUser>
     */
    protected List<IamUser> parseCsv(String reportString) {
        List<IamUser> iamUsers = new ArrayList<>();

        String[] reportLines = reportString.split("\n");

        for (int i=1; i<reportLines.length; i++) {
            iamUsers.add(convertStringToObject(reportLines[i]));
        }

        return iamUsers;
    }

    /**
     * convertStringToObject
     * String 안에 ","로 나누어진 각 항목을 iamUser 멤버변수에 입력 후 iamUser 인스턴스 생성
     * @param iamUserInfo
     * @return IamUser
     */
    private IamUser convertStringToObject(String iamUserInfo) {
        String[] info = iamUserInfo.split(",");

        return IamUser.builder()
                .user(info[0])
                .arn(info[1])
                .userCreationTime(info[2])
                .passwordEnabled(Boolean.parseBoolean(info[3]))
                .passwordLastUsed(info[4])
                .passwordLastChanged(info[5])
                .passwordNextRotation(info[6])
                .mfaActive(Boolean.parseBoolean(info[7]))
                .accessKey1Active(Boolean.parseBoolean(info[8]))
                .accessKey1LastRotated(info[9])
                .accessKey1LastUsedDate(info[10])
                .accessKey1LastUsedRegion(info[11])
                .accessKey1LastUsedService(info[12])
                .accessKey2Active(Boolean.parseBoolean(info[13]))
                .accessKey2LastRotated(info[14])
                .accessKey2LastUsedDate(info[15])
                .accessKey2LastUsedRegion(info[16])
                .accessKey2LastUsedService(info[17])
                .cert1Active(Boolean.parseBoolean(info[18]))
                .cert1LastRotated(info[19])
                .cert2Active(Boolean.parseBoolean(info[20]))
                .cert2LastRotated(info[21])
                .build();
    }
}
