package com.partimestudy.global.config;

import com.partimestudy.global.jwt.JwtImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 세션 토큰과 관련된 설정값을 관리한다.
 * TODO 고도화 - 추후 JWT 의 subject 이름 등을 여기에 추가해서 관리한다.
 */
@Component
public class SessionConfig {

    private static final String SECRET_KEY = "dfd416ae-9c50-11ed-a8fc-0242ac190d02";

    @Value("${session.expire-sec.access-token}")
    private Long ACCESS_TOKEN_EXPIRE_SEC;
    @Value("${session.expire-sec.refresh-token}")
    private Long REFRESH_TOKEN_EXPIRE_SEC;

    public Long getAccessTokenExpireSec(){
        return ACCESS_TOKEN_EXPIRE_SEC;
    }

    public Long getRefreshTokenExpireSec(){
        return REFRESH_TOKEN_EXPIRE_SEC;
    }

    public String getSecretKey() {
        return SECRET_KEY;
    }

    @Bean
    public JwtImpl jwtImpl() {
        return new JwtImpl(this);
    }

}
