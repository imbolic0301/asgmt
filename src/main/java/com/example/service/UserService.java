package com.example.service;

import com.example.exception.CommonExceptionTypes;
import com.example.persistence.entity.UserEntity;
import com.example.persistence.mapper.UserMapper;
import com.example.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;


    /**
     * 회원 가입 처리
     */
    public void addNewUser(UserDto.Request.SignUp request) {
        var newUser = UserEntity.initFrom(request);
        var exist = userMapper.findByEmail(request.getEmail());
        if(exist != null) throw CommonExceptionTypes.initCustomExceptionWith("사용 불가능한 이메일입니다.");
        userMapper.createNewUser(newUser);
    }

}
