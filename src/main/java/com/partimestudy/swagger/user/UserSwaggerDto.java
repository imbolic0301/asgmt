package com.partimestudy.swagger.user;

import com.partimestudy.domain.user.web.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

public class UserSwaggerDto {

    @ToString
    @Getter
    @Schema(description = "스웨거 - 회원 정보 조회 응답 예시")
    public static class UserInfoExample {
        @Schema(description = "응답 예시 아이디", implementation = UserDto.MyInfoResponse.class)
        private UserDto.MyInfoResponse data;
    }

}
