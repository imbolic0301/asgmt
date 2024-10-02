package com.partimestudy.global.jwt;

import com.partimestudy.domain.user.persistence.entity.UserEntity;
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
 * 컨트롤러에서 세션 정보를 조회할 때 사용하는 데이터 클래스
 * TODO 고도화 - 추후 RDB 가 아닌 redis 클러스터 등에서 관리
 */
@Getter
@ToString
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionInfo {
    private final String uuid;              // 토큰의 고유 ID
    private final Integer userId;           // 유저 PK
    private final String loginId;           // 로그인 아이디
    private final String userName;          // 유저 이름

    public SessionInfo(Claims claims) {
        this.uuid = claims.get("uuid", String.class);
        this.userId = claims.get("userId", Integer.class);
        this.loginId = claims.get("loginId", String.class);
        this.userName = claims.get("userName", String.class);
    }

    public static SessionInfo from(UserEntity entity) {
        return SessionInfo.builder()
                .uuid(UUID.randomUUID().toString())
                .userId(entity.getId())
                .userName(entity.getUserName())
                .loginId(entity.getLoginId())
                .build();
    }

    public Map<String, Object> toMapForClaims() {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", this.uuid);
        map.put("userId", this.userId);
        map.put("loginId", this.loginId);
        map.put("userName", this.userName);
        return map;
    }

}
