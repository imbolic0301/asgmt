package com.partimestudy.domain.user.service;

import com.partimestudy.domain.user.persistence.entity.UserEntity;
import com.partimestudy.domain.user.persistence.mapper.UserSignMapper;
import com.partimestudy.domain.user.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSignService {

    private final UserSignMapper userSignMapper;


    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Object signUpBy(UserDto.SignUpRequest request) {
        var entity = UserEntity.from(request);
        if(Boolean.TRUE.equals(userSignMapper.isDuplicatedLoginId(request.loginId()))) {
            throw new RuntimeException("중복 로그인 아이디 오류");
        }
        userSignMapper.createUserBy(entity);
//        var token = userTokenMapper.createNewToken();
//        return token;
        return null;
    }

    @Transactional(readOnly = true)
    public Object signInBy(UserDto.SignInRequest request) {
        UserEntity user = userSignMapper.getUserBySignIn(request);
        if(user == null) throw new RuntimeException("유저 없음 오류");
//        var token = userTokenMapper.createNewToken();
//        return token;
        return null;
    }

}
