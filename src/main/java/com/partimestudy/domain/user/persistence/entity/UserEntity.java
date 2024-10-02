package com.partimestudy.domain.user.persistence.entity;

import com.partimestudy.domain.user.web.dto.UserSignDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 유저 Entity
 */
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@Table("user")
public class UserEntity {
    private Integer id;                 // ID
    private String loginId;             // 로그인 아이디
    private String userName;            // 사용자명
    private String password;            // 암호

    // 회원 가입 요청으로부터 유저 생성 
    public static UserEntity from(UserSignDto.SignUpRequest request) {
        return UserEntity.builder()
                .loginId(request.getLoginId())
                .userName(request.getUserName())
                .password(request.getPassword())
                .build();
    }
    
}
