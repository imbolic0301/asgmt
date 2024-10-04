package com.partimestudy.global.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * init 된 세션 정보를 Response 에 제공하는 데이터 클래스
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class SessionJwt {
    @Schema(description = "세션 확인용 accessToken, JWT 인증 방식으로 사용")
    private String accessToken;
    @Schema(description = "세션 갱신용 refreshToken, API 호출로 갱신")
    private String refreshToken;
    @Schema(description = "생성일시 millis")
    private Long createMillis;
    @Schema(description = "accessToken JWT 의 유효시간 millis")
    private Long accessTokenExpireMillis;
    @Schema(description = "refreshToken JWT 의 유효시간 millis")
    private Long refreshTokenExpireMillis;

    @JsonIgnore
    public LocalDateTime getCreatedDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(createMillis), ZoneId.systemDefault());
    }

    @JsonIgnore
    public LocalDateTime getAccessTokenExpireDate() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(accessTokenExpireMillis), ZoneId.systemDefault());
    }

    @JsonIgnore
    public LocalDateTime getRefreshTokenExpireDate() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(refreshTokenExpireMillis), ZoneId.systemDefault());
    }

}
