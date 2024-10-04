package com.partimestudy.domain.user.service;

import com.partimestudy.domain.balance.service.UserBalanceService;
import com.partimestudy.domain.user.persistence.entity.UserEntity;
import com.partimestudy.domain.user.persistence.mapper.UserSignMapper;
import com.partimestudy.domain.user.web.dto.UserSignDto;
import com.partimestudy.global.cipher.CipherCore;
import com.partimestudy.global.jwt.SessionJwt;
import com.partimestudy.global.web.exception.ServiceExceptionTypes;
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
    private final UserTokenService userTokenService;
    private final UserBalanceService userBalanceService;
    private final CipherCore cipherCore;


    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SessionJwt signUpBy(UserSignDto.SignUpRequest request) throws Exception {
        request.initCipheredPassword(cipherCore.encryptSha256(request.getPassword(), request.getLoginId()));
        var newUser = UserEntity.from(request);
        if(Boolean.TRUE.equals(userSignMapper.isDuplicatedLoginId(request.getLoginId()))) {
            throw ServiceExceptionTypes.DUPLICATED_EMAIL_USER.toException();
        }
        userSignMapper.createUserBy(newUser);
        userBalanceService.initNewUserBalance(newUser);
        return userTokenService.createNewSessionJwt(newUser);
    }

    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SessionJwt signInBy(UserSignDto.SignInRequest request) throws Exception {
        request.initCipheredPassword(cipherCore.encryptSha256(request.getPassword(), request.getLoginId()));
        UserEntity existUser = userSignMapper.findUserBy(request);
        if(existUser == null) throw ServiceExceptionTypes.NOT_FOUND_USER.toException();
        return userTokenService.createNewSessionJwt(existUser);
    }

}
