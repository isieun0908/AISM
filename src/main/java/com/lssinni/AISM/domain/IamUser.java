package com.lssinni.AISM.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class IamUser {

    private String user;        // 사용자의 표시 이름

    private String arn;         // Amazon 리소스 이름

    private String userCreationTime;       // 사용자가 생성된 날짜 및 시간

    private Boolean passwordEnabled;               // 사용자의 암호 여부

    private String passwordLastUsed;       // 암호 마지막 사용한 날짜 및 시간

    private String passwordLastChanged;    // 암호 마지막 설정된 날짜 및 시간

    private String passwordNextRotation;   // 새 암호 설정 날짜 및 시간

    private Boolean mfaActive;             // MFA 사용 여부

    private Boolean accessKey1Active;            // access_key1 활동 상태 여부

    private String accessKey1LastRotated;    // access_key1 마지막 변경일

    private String accessKey1LastUsedDate;  // access_key1 최근 사용일

    private String accessKey1LastUsedRegion;       // access_key1 최근 사용 AWS Region

    private String accessKey1LastUsedService;      // access_key1 최근 사용 AWS Service

    private Boolean accessKey2Active;            // access_key2 활동 상태 여부

    private String accessKey2LastRotated;    // access_key2 마지막 변경일

    private String accessKey2LastUsedDate;  // access_key2 최근 사용일

    private String accessKey2LastUsedRegion;       // access_key2 최근 사용 AWS Region

    private String accessKey2LastUsedService;      // access_key2 최근 사용 AWS Service

    private Boolean cert1Active;          // 첫 번째 X.509 서명 인증서 상태 여부

    private String cert1LastRotated;      // 첫 번째 서명인증서 마지막 변경 날짜 및 시간

    private Boolean cert2Active;          // 두 번째 X.509 서명 인증서 상태 여부

    private String cert2LastRotated;      // 두 번째 서명인증서 마지막 변경 날짜 및 시간
}
