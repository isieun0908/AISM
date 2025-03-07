package com.lssinni.AISM.domain;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private byte[] content;

    public Report(byte[] content) {
        this.content = content;
    }

    /**
     * parseCsv
     * - csv형식의 byte 배열을 string으로 변환 후, IamUser 객체 리스트로 반환
     * @return List<IamUser>
     */
    public List<IamUser> parseCsv() {
        String reportString = new String(content);    // byte에서 string으로 변환
        String[] reportLines = reportString.split("\n");

        List<IamUser> iamUsers = new ArrayList<>();

        for (int i=1; i<reportLines.length; i++) {
            iamUsers.add(toObject(reportLines[i]));
        }
        return iamUsers;
    }

    /**
     * convertStringToObject
     * String 안에 ","로 나누어진 각 항목을 iamUser 멤버변수에 입력 후 iamUser 인스턴스 생성
     * @param iamUserInfo
     * @return IamUser
     */
    private IamUser toObject(String iamUserInfo) {
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
