package com.partimestudy.domain.user.web.dto;

import com.partimestudy.domain.user.persistence.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class UserDto {

    @ToString
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    @Getter
    @Schema(description = "회원 정보 조회")
    public static class MyInfoResponse {
        @Schema(description = "로그인 아이디", example = "jina")
        private String loginId;
        @Schema(description = "회원 이름", example = "홍길동")
        private String userName;

        public static MyInfoResponse from(UserEntity existUser) {
            return MyInfoResponse.builder()
                    .userName(existUser.getUserName())
                    .loginId(existUser.getLoginId())
                    .build();
        }
    }

}
