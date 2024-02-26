package com.example.web.controller;

import com.example.service.UserService;
import com.example.web.dto.CommonDto;
import com.example.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // 신규 회원 가입
    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody UserDto.Request.SignUp request) {
        request.validate();
        userService.addNewUser(request);
        return CommonDto.emptyResponse();
    }

}
