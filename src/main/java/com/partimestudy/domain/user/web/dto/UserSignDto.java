package com.partimestudy.domain.user.web.dto;

import com.partimestudy.global.web.exception.CustomExceptionTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class UserSignDto {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    @Schema(description = "회원 가입 요청")
    public static class SignUpRequest {
        @Schema(description = "로그인 아이디", example = "jina", requiredMode = Schema.RequiredMode.REQUIRED)
        private String loginId;
        @Schema(description = "회원 이름", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
        private String userName;
        @Schema(description = "비밀번호", example = "pass", requiredMode = Schema.RequiredMode.REQUIRED)
        private String password;

        public void validate() {
            // TODO - 세부 파라미터에 대한 검증 규칙 추가 필요
            if(this.password == null) throw CustomExceptionTypes.INVALID_PARAMETER_FAILURE_MESSAGE.init("올바르지 않은 비밀번호 양식입니다.");
        }

        public void initCipheredPassword(String password) {
            this.password = password;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    @Schema(description = "로그인 요청")
    public static class SignInRequest {
        @Schema(description = "로그인 아이디", example = "jina", requiredMode = Schema.RequiredMode.REQUIRED)
        private String loginId;
        @Schema(description = "비밀번호", example = "pass", requiredMode = Schema.RequiredMode.REQUIRED)
        private String password;

        public void validate() {
            // TODO - 세부 파라미터에 대한 검증 규칙 추가 필요
            if(this.password == null) throw CustomExceptionTypes.INVALID_PARAMETER_FAILURE_MESSAGE.init("올바르지 않은 비밀번호 양식입니다.");
        }

        public void initCipheredPassword(String password) {
            this.password = password;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    @Schema(description = "토큰 갱신 요청")
    public static class TokenRefreshRequest {
        @Schema(description = "로그인 아이디", example = "jina", requiredMode = Schema.RequiredMode.REQUIRED)
        private String refreshToken;

        public void validate() {
            // TODO - 세부 파라미터에 대한 검증 규칙 추가 필요
            if(this.refreshToken == null) throw CustomExceptionTypes.INVALID_PARAMETER_FAILURE_MESSAGE.init("올바르지 않은 토큰 양식입니다.");
        }
    }

}
