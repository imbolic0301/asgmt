package com.example.asgmt.user;

import com.example.web.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

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
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(userWith11CharacterPassword::validate)
                .isInstanceOf(NullPointerException.class);
    }

    private UserDto.Request.SignUp initPasswordFrom(String password) {
        return UserDto.Request.SignUp.builder()
                .password(password)
                .build();
    }
}