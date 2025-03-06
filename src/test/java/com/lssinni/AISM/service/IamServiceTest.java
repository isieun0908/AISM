package com.lssinni.AISM.service;

import com.lssinni.AISM.domain.IamUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class IamServiceTest {

    @Autowired IamService iamService;

    @Test
    void generateCredentialReport() {
    }

    @Test
    void convertByteToString() {
        String testString = "my name is lssinni";

        byte[] testByte = testString.getBytes();

        String resultString = iamService.convertByteToString(testByte);

        Assertions.assertEquals(testString, resultString);
    }

    @Test
    void parseCsv() {
        String testString = "user,arn,user_creation_time,password_enabled,password_last_used,password_last_changed,password_next_rotation,mfa_active,access_key_1_active,access_key_1_last_rotated,access_key_1_last_used_date,access_key_1_last_used_region,access_key_1_last_used_service,access_key_2_active,access_key_2_last_rotated,access_key_2_last_used_date,access_key_2_last_used_region,access_key_2_last_used_service,cert_1_active,cert_1_last_rotated,cert_2_active,cert_2_last_rotated\n" +
                "root,arn:aws:iam::1234:root,2025-01-01T00:00:00Z,true,2025-01-01T00:00:00Z,2025-01-01T00:00:00Z,not_supported,true,false,N/A,N/A,N/A,N/A,false,N/A,N/A,N/A,N/A,false,N/A,false,N/A\n" +
                "user1,arn:aws:iam::5678:user/user1,2025-01-01T00:00:00Z,true,2025-01-01T00:00:00Z,2025-01-01T00:00:00Z,N/A,false,true,2025-01-01T00:00:00Z,2025-01-01T00:00:00Z,us-east-1,iam,false,N/A,N/A,N/A,N/A,false,N/A,false,N/A";

        List<IamUser> iamUsers = iamService.parseCsv(testString);

        StringBuilder sb = new StringBuilder();

        sb.append("user,arn,user_creation_time,password_enabled,password_last_used,password_last_changed,password_next_rotation,mfa_active,access_key_1_active,access_key_1_last_rotated,access_key_1_last_used_date,access_key_1_last_used_region,access_key_1_last_used_service,access_key_2_active,access_key_2_last_rotated,access_key_2_last_used_date,access_key_2_last_used_region,access_key_2_last_used_service,cert_1_active,cert_1_last_rotated,cert_2_active,cert_2_last_rotated");
        for (IamUser iamUser : iamUsers) {
            sb.append("\n");
            sb.append(iamUser.getUser()+",");
            sb.append(iamUser.getArn()+",");
            sb.append(iamUser.getUser_creation_time()+",");
            sb.append(iamUser.getPassword_enabled()+",");
            sb.append(iamUser.getPassword_last_used()+",");
            sb.append(iamUser.getPassword_last_changed()+",");
            sb.append(iamUser.getPassword_next_rotation()+",");
            sb.append(iamUser.getMfa_active()+",");
            sb.append(iamUser.getAccess_key_1_active()+",");
            sb.append(iamUser.getAccess_key_1_last_rotated()+",");
            sb.append(iamUser.getAccess_key_1_last_used_date()+",");
            sb.append(iamUser.getAccess_key_1_last_used_region()+",");
            sb.append(iamUser.getAccess_key_1_last_used_service()+",");
            sb.append(iamUser.getAccess_key_2_active()+",");
            sb.append(iamUser.getAccess_key_2_last_rotated()+",");
            sb.append(iamUser.getAccess_key_2_last_used_date()+",");
            sb.append(iamUser.getAccess_key_2_last_used_region()+",");
            sb.append(iamUser.getAccess_key_2_last_used_service()+",");
            sb.append(iamUser.getCert_1_active()+",");
            sb.append(iamUser.getCert_1_last_rotated()+",");
            sb.append(iamUser.getCert_2_active()+",");
            sb.append(iamUser.getCert_2_last_rotated());
        }
        String resultString = sb.toString();

        System.out.println(resultString);

        Assertions.assertEquals(testString, sb.toString());
    }
}