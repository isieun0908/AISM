package com.lssinni.AISM.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class IamUser {

    private String user;        // 사용자의 표시 이름

    private String arn;         // Amazon 리소스 이름

    private String user_creation_time;       // 사용자가 생성된 날짜 및 시간

    private Boolean password_enabled;               // 사용자의 암호 여부

    private String password_last_used;       // 암호 마지막 사용한 날짜 및 시간

    private String password_last_changed;    // 암호 마지막 설정된 날짜 및 시간

    private String password_next_rotation;   // 새 암호 설정 날짜 및 시간

    private Boolean mfa_active;             // MFA 사용 여부

    private Boolean access_key_1_active;            // access_key1 활동 상태 여부

    private String access_key_1_last_rotated;    // access_key1 마지막 변경일

    private String access_key_1_last_used_date;  // access_key1 최근 사용일

    private String access_key_1_last_used_region;       // access_key1 최근 사용 AWS Region

    private String access_key_1_last_used_service;      // access_key1 최근 사용 AWS Service

    private Boolean access_key_2_active;            // access_key2 활동 상태 여부

    private String access_key_2_last_rotated;    // access_key2 마지막 변경일

    private String access_key_2_last_used_date;  // access_key2 최근 사용일

    private String access_key_2_last_used_region;       // access_key2 최근 사용 AWS Region

    private String access_key_2_last_used_service;      // access_key2 최근 사용 AWS Service

    private Boolean cert_1_active;          // 첫 번째 X.509 서명 인증서 상태 여부

    private String cert_1_last_rotated;      // 첫 번째 서명인증서 마지막 변경 날짜 및 시간

    private Boolean cert_2_active;          // 두 번째 X.509 서명 인증서 상태 여부

    private String cert_2_last_rotated;      // 두 번째 서명인증서 마지막 변경 날짜 및 시간
}
