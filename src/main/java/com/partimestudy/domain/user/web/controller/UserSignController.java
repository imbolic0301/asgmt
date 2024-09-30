package com.partimestudy.domain.user.web.controller;


import com.partimestudy.domain.user.service.UserSignService;
import com.partimestudy.domain.user.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sign")
@Slf4j
@RequiredArgsConstructor
public class UserSignController {

    private final UserSignService signService;


    // TODO - 회원 가입
    @PostMapping("/up")
    public ResponseEntity<Object> signUp(UserDto.SignUpRequest request) {
        request.validate();
        var response = signService.signUpBy(request);
        return ResponseEntity.ok().build();
    }

    // TODO - 회원 로그인
    public ResponseEntity<Object> signIn(UserDto.SignInRequest request) {
        var response = signService.signInBy(request);
        return ResponseEntity.ok().build();
    }


}
