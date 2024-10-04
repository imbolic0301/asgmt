package com.partimestudy.domain.deposit.service;


import com.partimestudy.domain.balance.service.UserBalanceService;
import com.partimestudy.domain.challenge.persistence.entity.ChallengeEntity;
import com.partimestudy.domain.challenge.persistence.mapper.ChallengeMapper;
import com.partimestudy.domain.challenge.web.dto.ChallengeDto;
import com.partimestudy.domain.deposit.persistence.DepositMapper;
import com.partimestudy.domain.deposit.persistence.UserDepositHistoryEntity;
import com.partimestudy.global.jwt.SessionInfo;
import com.partimestudy.global.web.dto.CommonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepositService {

    private final DepositMapper depositMapper;
    private final ChallengeMapper challengeMapper;
    private final UserBalanceService userBalanceService;


    // 챌린지 참여(보증 적용) 이력 조회
    @Transactional(readOnly = true)
    public CommonDto.ListResponse<ChallengeDto.MyChallengeInfo> findChallengesByConditions(
            SessionInfo sessionInfo, ChallengeDto.MyChallengePageRequest pageRequest) {
        Integer totalCount = depositMapper.countByConditions(sessionInfo.getUserId());
        List<ChallengeDto.MyChallengeInfo> deposits = Collections.emptyList();
        if(totalCount > 0) {
            deposits = depositMapper.findByConditions(sessionInfo.getUserId(), pageRequest);
            deposits.forEach(e -> e.initSchedules(challengeMapper.findSchedulesBy(e.getChallengeId())));
        }
        var pageInfo =
                CommonDto.PageInfo.builder()
                        .page(pageRequest.getPage())
                        .showCount(pageRequest.getShowCount())
                        .totalCount(totalCount)
                        .build();
        return CommonDto.ListResponse.from(deposits, pageInfo);
    }

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