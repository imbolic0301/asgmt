package com.partimestudy.domain.challenge.service;

import com.partimestudy.domain.balance.service.UserBalanceService;
import com.partimestudy.domain.challenge.persistence.entity.ChallengeEntity;
import com.partimestudy.domain.challenge.persistence.entity.UserChallengeScheduleEntity;
import com.partimestudy.domain.challenge.persistence.mapper.ChallengeMapper;
import com.partimestudy.domain.challenge.web.dto.ChallengeDto;
import com.partimestudy.domain.deposit.service.DepositService;
import com.partimestudy.global.jwt.SessionInfo;
import com.partimestudy.global.web.dto.CommonDto;
import com.partimestudy.global.web.exception.ServiceExceptionTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeService {

    private final ChallengeMapper challengeMapper;
    private final UserBalanceService userBalanceService;
    private final DepositService depositService;
    
    
    // 챌린지 참여
    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void participateChallenge(SessionInfo sessionInfo, ChallengeDto.ChallengeParticipateRequest request) {
        // 0. 현재 등록된 챌린지가 있는지, 중복 확인
        Boolean isExistActiveChallenge = depositService.isExistActiveChallenge(sessionInfo, request);
        if(Boolean.TRUE.equals(isExistActiveChallenge)) {
            throw ServiceExceptionTypes.CHALLENGE_CAN_ACTIVE_ONLY_ONE.toException();
        }
        // 1. 챌린지 정보를 조회 후 1차 검증
        var challenge = challengeMapper.findById(request.getChallengeId());
        if(challenge == null) throw ServiceExceptionTypes.RESOURCE_NOT_FOUND.toException();
        request.validateBy(challenge);
        // 2. 현재 잔고를 기준으로 검증.
        BigDecimal currentBalance = userBalanceService.getCurrentUserBalance(sessionInfo.getUserId());
        if(currentBalance.compareTo(challenge.getDepositMin()) < 0) {
            throw ServiceExceptionTypes.LACK_OF_AVAILABLE_DEPOSIT.toException();
        }
        // 3. 유저의 보증금 사용 이력 생성
        Long depositHistoryId = depositService.createDeposit(sessionInfo, request, challenge);
        // 4. 챌린지 등록 완료 및 차감 처리
        var scheduleEntities = 
                request.getChallengeSchedules().stream()
                        .map(e -> UserChallengeScheduleEntity.from(depositHistoryId, challenge, e))
                        .toList();
        challengeMapper.createSchedules(scheduleEntities);
    }

    @Transactional(readOnly = true)
    public CommonDto.ListResponse<ChallengeDto.ChallengeElement> findChallengesByConditions(ChallengeDto.ChallengePageRequest pageRequest) {
        Integer totalCount = challengeMapper.countByConditions();
        List<ChallengeEntity> entities = Collections.emptyList();
        if(totalCount > 0) {
            entities = challengeMapper.findByConditions(pageRequest);
        }
        var pageInfo =
                CommonDto.PageInfo.builder()
                    .page(pageRequest.getPage())
                    .showCount(pageRequest.getShowCount())
                    .totalCount(totalCount)
                    .build();
        List<ChallengeDto.ChallengeElement> list =
                entities.isEmpty()
                    ? Collections.emptyList()
                    : entities.stream()
                        .map(ChallengeDto.ChallengeElement::from)
                        .toList();
        return CommonDto.ListResponse.from(list, pageInfo);
    }
}
