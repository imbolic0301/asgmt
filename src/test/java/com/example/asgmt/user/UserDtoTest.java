package com.example.asgmt.user;

import com.example.exception.CustomException;
import com.example.web.dto.UserDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserDtoTest {

    // 패스워드 길이는 6~10자 내외일 것
    @Test
    void password_length_must_be_between_6_and_10 () {
        // given
        String fiveCharacterString = "12345";
        String elevenCharacterString = "12345678901";

        // when
        var userWith5CharacterPassword = initPasswordFrom(fiveCharacterString);
        var userWith11CharacterPassword = initPasswordFrom(elevenCharacterString);

        // then
        assertThatThrownBy(userWith5CharacterPassword::validate)
                .isInstanceOf(CustomException.class);
        assertThatThrownBy(userWith11CharacterPassword::validate)
                .isInstanceOf(CustomException.class);
    }

    // 패스워드는 소문자, 대문자, 숫자 중 2 가지 이상을 사용해야 한다.
    @Test
    void password_must_contain_2_types_in_upperCase_lowerCase_digit () {
        // given
        var validPasswords = List.of("abc123", "Abc123", "ABC123", "abcDEF");
        var invalidPasswords = List.of("abcdef", "ABCDEF", "123456");

        // when
        var validUsers = validPasswords.stream().map(this::initPasswordFrom).toList();
        var invalidUsers = invalidPasswords.stream().map(this::initPasswordFrom).toList();

        // then
        validUsers.forEach(e -> e.validate());
        for(var user : invalidUsers) {
            assertThatThrownBy(user::validate)
                    .isInstanceOf(CustomException.class);
        }
    }

    private UserDto.Request.SignUp initPasswordFrom(String password) {
        return UserDto.Request.SignUp.builder()
                .password(password)
                .build();
    }
}