package com.partimestudy.global.jwt;

import com.partimestudy.domain.user.persistence.entity.UserEntity;
import com.partimestudy.global.web.exception.ServiceExceptionTypes;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 세션 정보 기준인 accessToken 을 갱신할 때 쓰는 RefreshToken
 */
@Getter
@ToString
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {
    private final String uuid;              // 토큰의 고유 ID
    private final Integer userId;           // 유저 PK

    public RefreshToken(Claims claims) {
        validate(claims);
        this.uuid = claims.get("uuid", String.class);
        this.userId = claims.get("userId", Integer.class);
    }

    public static RefreshToken from(UserEntity entity) {
        return RefreshToken.builder()
                .uuid(UUID.randomUUID().toString())
                .userId(entity.getId())
                .build();
    }

    public Map<String, Object> toMapForClaims() {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", this.uuid);
        map.put("userId", this.userId);
        return map;
    }
    
    // TODO - 추후 JWT 토큰 생성시 사용하는 subject 값과 동일한 Literal 을 쓰도록 개선 필요
    private void validate(Claims claims) {
        if(!"refresh".equals(claims.getSubject())) {
            throw ServiceExceptionTypes.INVALID_TOKEN.toException();
        }
    }

}
