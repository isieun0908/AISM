package com.lssinni.AISM;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.iam.IamClient;

@Configuration
public class SpringConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String accessSecret;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public IamClient iamClient() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, accessSecret);

        return IamClient.builder()
            .region(Region.of(region))  // 리전 설정
            .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))    // 기본 자격 증명 공급자 사용
            .build();
    }
}
