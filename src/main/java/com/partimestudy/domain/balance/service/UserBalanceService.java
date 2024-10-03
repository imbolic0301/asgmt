package com.partimestudy.domain.balance.service;

import com.partimestudy.domain.balance.persistence.UserBalanceMapper;
import com.partimestudy.domain.user.persistence.entity.UserEntity;
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
public class UserBalanceService {

    private final UserBalanceMapper userBalanceMapper;


    // 회원 가입시 신규 회원에 대한 잔액 row 초기화
    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public void initNewUserBalance(UserEntity user) {
        if(user == null || user.getId() == null) throw ServiceExceptionTypes.DEBUGGING_NEED_ERROR.toException();
        userBalanceMapper.initNewUserBalance(user);
    }

}
