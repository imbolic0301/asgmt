package com.example.asgmt.user;

import com.example.exception.CustomException;
import com.example.service.UserService;
import com.example.web.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

// TODO - DB 데이터를 조작하는 테스트 코드는 현재 작성 금지, 추후 H2 인메모리 DB 를 사용하는 테스트 환경 구축 필요
//@SpringBootTest
//class UserServiceTest {
//
//    @Autowired
//    UserService userService;
//
//
//    // 회원가입시, 회원은 반드시 중복되지 않은 이메일을 가지고 있어야 한다.
//    @Test
//    void user_email_is_UK() {
//        //given
//        String duplicatedEmail = "duplicate@email.com";
//        var requests = init2SignUpRequestWith(duplicatedEmail);
//
//        //when
//        userService.addNewUser(requests.get(0));
//
//        //then
//        var signUpRequestWithDuplicatedEmail = requests.get(1);
//        assertThatThrownBy(() -> userService.addNewUser(signUpRequestWithDuplicatedEmail))
//                .isInstanceOf(CustomException.class);
//    }
//
//    private List<UserDto.Request.SignUp> init2SignUpRequestWith(String duplicatedEmail) {
//        var commonBuilder = UserDto.Request.SignUp.builder()
//                .email(duplicatedEmail)
//                .password("validPass")
//                .phone("010-1234-2345");
//        return
//                Stream.of("user1", "user2")
//                        .map(str -> {
//                            var req = commonBuilder.name(str).build();
//                            req.validate();
//                            return req;
//                        }).toList();
//    }
//}
