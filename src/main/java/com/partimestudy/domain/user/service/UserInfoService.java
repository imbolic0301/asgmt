package com.partimestudy.domain.user.service;

import com.partimestudy.domain.user.persistence.entity.UserEntity;
import com.partimestudy.domain.user.persistence.mapper.UserMapper;
import com.partimestudy.domain.user.web.dto.UserDto;
import com.partimestudy.global.jwt.SessionInfo;
import com.partimestudy.global.web.exception.ServiceExceptionTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {

    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserDto.MyInfoResponse findMyInfoBy(SessionInfo sessionInfo) {
        UserEntity existUser = userMapper.findUserById(sessionInfo.getUserId());
        if(existUser == null) throw ServiceExceptionTypes.NOT_FOUND_USER.toException();
        return UserDto.MyInfoResponse.from(existUser);
    }
}
