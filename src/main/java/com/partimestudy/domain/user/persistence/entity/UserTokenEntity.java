package com.partimestudy.domain.user.persistence.entity;

import com.partimestudy.global.jwt.RefreshToken;
import com.partimestudy.global.jwt.SessionInfo;
import com.partimestudy.global.jwt.SessionJwt;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 유저 토큰 Entity
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@ToString
//@Table("user_token_history")
public class UserTokenEntity {
    private Long id;
    private Integer userId;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokenExpireAt;
    private LocalDateTime refreshTokenExpireAt;

    public static UserTokenEntity from(UserEntity user, SessionInfo sessionInfo, RefreshToken refreshToken, SessionJwt sessionJwt) {
        return UserTokenEntity.builder()
                .userId(user.getId())
                .accessToken(sessionInfo.getUuid())
                .accessTokenExpireAt(sessionJwt.getAccessTokenExpireDate())
                .refreshToken(refreshToken.getUuid())
                .refreshTokenExpireAt(sessionJwt.getRefreshTokenExpireDate())
                .build();
    }
}