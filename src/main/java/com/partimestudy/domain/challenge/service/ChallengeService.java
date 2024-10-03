package com.partimestudy.domain.challenge.service;

import com.partimestudy.domain.challenge.persistence.entity.ChallengeEntity;
import com.partimestudy.domain.challenge.persistence.mapper.ChallengeMapper;
import com.partimestudy.domain.challenge.web.dto.ChallengeDto;
import com.partimestudy.global.jwt.SessionInfo;
import com.partimestudy.global.web.dto.CommonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeService {

    private final ChallengeMapper challengeMapper;

    // TODO 챌린지 도전
    public void participateChallenge(SessionInfo sessionInfo, ChallengeDto.ChallengeParticipateRequest request) {

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
