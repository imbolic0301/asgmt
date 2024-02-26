package com.example.web.dto;

import com.example.exception.CommonExceptionTypes;
import io.micrometer.common.util.StringUtils;
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
            @Builder.Default
            private boolean isValid = false;

            // 입력값 검증 로직 처리
            public void validate() {
                // TODO - 추후 이름 관련 검증 비즈니스 로직 적용 필요
                if(StringUtils.isBlank(this.name))
                    throw CommonExceptionTypes.initCustomExceptionWith("이름을 입력해주세요.");

                // TODO - 추후 이메일 관련 검증 비즈니스 로직 적용 필요
                if(StringUtils.isBlank(this.email))
                    throw CommonExceptionTypes.initCustomExceptionWith("이메일을 입력해주세요.");

                // TODO - 추후 전화번호 관련 검증 비즈니스 로직 적용 필요
                if(StringUtils.isBlank(this.phone))
                    throw CommonExceptionTypes.initCustomExceptionWith("전화번호를 입력해주세요.");

                if(StringUtils.isBlank(this.password))
                    throw CommonExceptionTypes.initCustomExceptionWith("비밀번호를 입력해주세요.");

                // 비밀번호에서 앞 뒤 공백 제거
                this.password = this.password.trim();

                // 비밀번호 조건 1 : 비밀번호의 길이는 6~10 일 것
                int length = this.password.length();
                boolean isValidLength = 5 < length && length < 11;
                if(!isValidLength) {
                    throw CommonExceptionTypes.initCustomExceptionWith("비밀번호는 6~10 자 내외여야 합니다.");
                }

                // 비밀번호 조건 2 : 영문 소문자 / 영문 대문자 / 숫자 의 3가지 유형 중, 2가지 이상을 조합할 것
                if(!validateCharacterTypeCombination(this.password))
                    throw CommonExceptionTypes.initCustomExceptionWith(
                            "비밀번호는 영문 소문자, 영문 대문자, 숫자 의 3가지 유형 중 2가지 이상을 사용해야 합니다."
                    );

                this.isValid = true;
            }

            public String password() {
                if(!isValid) throw CommonExceptionTypes.DEBUGGING_NEED_ERROR.toException();
                // TODO 추후 단방향 암호화 로직 적용
                return this.password;
            }

            // 문자 조합이 올바른지 확인
            private boolean validateCharacterTypeCombination(String str) {
                boolean hasLowercase = false;       // 소문자 포함 여부
                boolean hasUppercase = false;       // 대문자 포함 여부
                boolean hasDigit = false;           // 숫자 포함 여부

                for (char ch : str.toCharArray()) {
                    if (Character.isLowerCase(ch)) {
                        hasLowercase = true;
                    } else if (Character.isUpperCase(ch)) {
                        hasUppercase = true;
                    } else if (Character.isDigit(ch)) {
                        hasDigit = true;
                    }

                    // 이미 두 가지 이상의 유형이 발견된 경우 true 반환
                    if ((hasLowercase && hasUppercase) || (hasLowercase && hasDigit) || (hasUppercase && hasDigit)) {
                        return true;
                    }
                }

                // 두 가지 이상의 유형이 발견되지 않은 경우 false 반환
                return false;
            }
        }

    }
}
