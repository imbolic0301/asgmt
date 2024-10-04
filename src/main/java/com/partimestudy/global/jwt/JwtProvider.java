package com.partimestudy.global.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * JWT 구현체를 내부에서 관리하고 외부에 관련 기능을 제공
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtImpl jwtImpl;


    // 토큰 생성
    public SessionJwt sessionJwtFrom(Long timeMillis, SessionInfo sessionInfo, RefreshToken refreshToken) {
        return jwtImpl.from(timeMillis, sessionInfo, refreshToken);
    }
    
    // 토큰 디코딩 후 유저 세션 정보 조회
    public SessionInfo sessionInfoFrom(String jws) {
        return new SessionInfo(jwtImpl.decode(jws));
    }

    // 리프레시 토큰의 JWS 값을 디코딩 후 uuid 리턴
    public RefreshToken refreshTokenFrom(String jws) {
        return new RefreshToken(jwtImpl.decode(jws));
    }

}
