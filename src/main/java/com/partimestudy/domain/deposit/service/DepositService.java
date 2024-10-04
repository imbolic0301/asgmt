package com.partimestudy.domain.deposit.service;


import com.partimestudy.domain.balance.service.UserBalanceService;
import com.partimestudy.domain.challenge.persistence.entity.ChallengeEntity;
import com.partimestudy.domain.challenge.web.dto.ChallengeDto;
import com.partimestudy.domain.deposit.persistence.DepositMapper;
import com.partimestudy.domain.deposit.persistence.UserDepositHistoryEntity;
import com.partimestudy.global.jwt.SessionInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepositService {

    private final DepositMapper depositMapper;
    private final UserBalanceService userBalanceService;


    // 보증 적용
    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE, propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public Long createDeposit(SessionInfo sessionInfo, ChallengeDto.ChallengeParticipateRequest request, ChallengeEntity challenge) {
        var entity = UserDepositHistoryEntity.from(sessionInfo, request, challenge);
        depositMapper.createHistory(entity);
        userBalanceService.moveBalanceToDeposit(entity.getUserId(), entity.getPayAmount());
        return entity.getId();
    }

    // 이미 보증금이 걸려있는 챌린지가 있는지 확인
    @Transactional(readOnly = true)
    public Boolean isExistActiveChallenge(SessionInfo sessionInfo, ChallengeDto.ChallengeParticipateRequest request) {
        return depositMapper.findExistActiveDeposit(sessionInfo.getUserId(), request.getChallengeId());
    }
}