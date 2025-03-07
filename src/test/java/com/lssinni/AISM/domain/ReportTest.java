package com.lssinni.AISM.domain;

import com.lssinni.AISM.service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.core.SdkBytes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportTest {

    @Test
    void parseCsv() {
        // given
        String test = "user,arn,user_creation_time,password_enabled,password_last_used,password_last_changed,password_next_rotation,mfa_active,access_key_1_active,access_key_1_last_rotated,access_key_1_last_used_date,access_key_1_last_used_region,access_key_1_last_used_service,access_key_2_active,access_key_2_last_rotated,access_key_2_last_used_date,access_key_2_last_used_region,access_key_2_last_used_service,cert_1_active,cert_1_last_rotated,cert_2_active,cert_2_last_rotated\n" +
                "root,arn:aws:iam::1234:root,2025-01-01T00:00:00Z,true,2025-01-01T00:00:00Z,2025-01-01T00:00:00Z,not_supported,true,false,N/A,N/A,N/A,N/A,false,N/A,N/A,N/A,N/A,false,N/A,false,N/A\n" +
                "user1,arn:aws:iam::5678:user/user1,2025-01-01T00:00:00Z,true,2025-01-01T00:00:00Z,2025-01-01T00:00:00Z,N/A,false,true,2025-01-01T00:00:00Z,2025-01-01T00:00:00Z,us-east-1,iam,false,N/A,N/A,N/A,N/A,false,N/A,false,N/A";
        byte[] testReport = test.getBytes();

        // when
        Report report = new Report(testReport);
        List<IamUser> iamUsers = report.parseCsv();

        // then
        StringBuilder sb = new StringBuilder();
        sb.append("user,arn,user_creation_time,password_enabled,password_last_used,password_last_changed,password_next_rotation,mfa_active,access_key_1_active,access_key_1_last_rotated,access_key_1_last_used_date,access_key_1_last_used_region,access_key_1_last_used_service,access_key_2_active,access_key_2_last_rotated,access_key_2_last_used_date,access_key_2_last_used_region,access_key_2_last_used_service,cert_1_active,cert_1_last_rotated,cert_2_active,cert_2_last_rotated");
        for (IamUser iamUser : iamUsers) {
            sb.append("\n");
            sb.append(iamUser.getUser()+",");
            sb.append(iamUser.getArn()+",");
            sb.append(iamUser.getUserCreationTime()+",");
            sb.append(iamUser.getPasswordEnabled()+",");
            sb.append(iamUser.getPasswordLastUsed()+",");
            sb.append(iamUser.getPasswordLastChanged()+",");
            sb.append(iamUser.getPasswordNextRotation()+",");
            sb.append(iamUser.getMfaActive()+",");
            sb.append(iamUser.getAccessKey1Active()+",");
            sb.append(iamUser.getAccessKey1LastRotated()+",");
            sb.append(iamUser.getAccessKey1LastUsedDate()+",");
            sb.append(iamUser.getAccessKey1LastUsedRegion()+",");
            sb.append(iamUser.getAccessKey1LastUsedService()+",");
            sb.append(iamUser.getAccessKey2Active()+",");
            sb.append(iamUser.getAccessKey2LastRotated()+",");
            sb.append(iamUser.getAccessKey2LastUsedDate()+",");
            sb.append(iamUser.getAccessKey2LastUsedRegion()+",");
            sb.append(iamUser.getAccessKey2LastUsedService()+",");
            sb.append(iamUser.getCert1Active()+",");
            sb.append(iamUser.getCert1LastRotated()+",");
            sb.append(iamUser.getCert2Active()+",");
            sb.append(iamUser.getCert2LastRotated());
        }
        String result = sb.toString();

        Assertions.assertEquals(test, result);
    }
}