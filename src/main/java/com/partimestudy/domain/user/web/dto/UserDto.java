package com.partimestudy.domain.user.web.dto;

public class UserDto {
    public record SignUpRequest(
            String loginId
            , String username
            , String password
    ) {
        public void validate() {
            // TODO 비밀번호 검증 로직
            if(this.password == null) throw new RuntimeException("비밀번호는 필수입력입니다.");
        }
    }

    public record SignInRequest(
            String loginId
            , String password
    ) {}

}
