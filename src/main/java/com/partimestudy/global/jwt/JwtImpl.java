package com.partimestudy.global.jwt;

import com.partimestudy.global.config.SessionConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.function.Function;

/**
 * JWT 라이브러리를 직접적으로 활용하여 JWT 를 다루는 클래스
 */
public class JwtImpl {

    private final SecretKey SECRET_KEY;

    private final String ISSUER = "demo-api";
    private final String ACCESS_KEY_SUBJECT = "access";
    private final String REFRESH_KEY_SUBJECT = "refresh";
    private final Long ACCESS_TOKEN_EXPIRE_MILLISECONDS;
    private final Long REFRESH_TOKEN_EXPIRE_MILLISECONDS;
    private final Function<Long, Date> ACCESS_TOKEN_EXP_DATE_GENERATOR;
    private final Function<Long, Date> REFRESH_TOKEN_EXP_DATE_GENERATOR;


    public JwtImpl(SessionConfig sessionConfig) {
        byte[] keyBytes = sessionConfig.getSecretKey().getBytes(StandardCharsets.UTF_8);
        this.SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
        this.ACCESS_TOKEN_EXPIRE_MILLISECONDS = sessionConfig.getAccessTokenExpireSec() * 1000L;
        this.REFRESH_TOKEN_EXPIRE_MILLISECONDS = sessionConfig.getRefreshTokenExpireSec() * 1000L;
        this.ACCESS_TOKEN_EXP_DATE_GENERATOR = (Long millis) -> new Date(millis + ACCESS_TOKEN_EXPIRE_MILLISECONDS);
        this.REFRESH_TOKEN_EXP_DATE_GENERATOR = (Long millis) -> new Date(millis + REFRESH_TOKEN_EXPIRE_MILLISECONDS);
    }

    public Claims decode(String jws) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(jws)
                .getBody();
    }

    public SessionJwt from(Long timeMillis, SessionInfo sessionInfo, RefreshToken refreshToken) {
        return SessionJwt.builder()
                .createMillis(timeMillis)
                .accessToken(accessTokenJwsFrom(timeMillis, sessionInfo))
                .accessTokenExpireMillis(timeMillis + ACCESS_TOKEN_EXPIRE_MILLISECONDS)
                .refreshToken(refreshTokenJwsFrom(timeMillis, refreshToken))
                .refreshTokenExpireMillis(timeMillis + REFRESH_TOKEN_EXPIRE_MILLISECONDS)
                .build();
    }

    private String accessTokenJwsFrom(Long timeMillis, SessionInfo sessionInfo) {
        return commonJwt(timeMillis)
                .setId(sessionInfo.getUuid())
                .setSubject(ACCESS_KEY_SUBJECT)
                .setExpiration(ACCESS_TOKEN_EXP_DATE_GENERATOR.apply(timeMillis))
                .addClaims(sessionInfo.toMapForClaims())
                .compact();
    }

    private String refreshTokenJwsFrom(Long timeMillis, RefreshToken refreshToken) {
        return commonJwt(timeMillis)
                .setId(refreshToken.getUuid())
                .setSubject(REFRESH_KEY_SUBJECT)
                .setExpiration(REFRESH_TOKEN_EXP_DATE_GENERATOR.apply(timeMillis))
                .addClaims(refreshToken.toMapForClaims())
                .compact();
    }

    private JwtBuilder commonJwt(Long createdMillis) {
        return Jwts.builder()
                .setHeader(Collections.singletonMap("typ", "JWT"))
                .setIssuer(ISSUER)
                .setIssuedAt(new Date(createdMillis))
                .signWith(SECRET_KEY);
    }

}
