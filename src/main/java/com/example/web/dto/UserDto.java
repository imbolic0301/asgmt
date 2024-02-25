package com.example.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class UserDto {

    public static class Request {

        @Builder
        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        @Getter
        @ToString
        public static class SignUp {
            private String name;
            private String email;
            private String phone;
            private String password;

            public void validate() {
                // TODO 요청 필드 검증
            }

            public String password() {
                // TODO 추후 단방향 암호화 로직 적용
                return this.password;
            }
        }

    }
}
