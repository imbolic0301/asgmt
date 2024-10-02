package com.partimestudy.swagger.session;

import com.partimestudy.global.jwt.SessionJwt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

public class SessionSwaggerDto {


    @ToString
    @Getter
    @Schema(description = "스웨거 회원 세션 토큰 응답 예시")
    public static class SessionJwtExample {
        @Schema(description = "응답 예시 아이디", implementation = SessionJwt.class)
        private SessionJwt data;
    }

}
