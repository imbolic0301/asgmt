package com.example.persistence.entity;


import com.example.web.dto.UserDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 회원 정보 entity
 */
//@Table("user")
@Getter
@ToString
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;

    public static UserEntity initFrom(UserDto.Request.SignUp req) {
        return UserEntity.builder()
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .password(req.password())
                .build();
    }
}