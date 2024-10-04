package com.partimestudy.domain.deposit.persistence;

import com.partimestudy.domain.challenge.persistence.entity.ChallengeEntity;
import com.partimestudy.domain.challenge.web.dto.ChallengeDto;
import com.partimestudy.global.jwt.SessionInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 보증금 이력 Entity
 */
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@Table("user_balance")
public class UserDepositHistoryEntity {
    private Long id;
    private Integer userId;
    private Long challengeId;
    private BigDecimal payAmount;           // TODO 추후 할인 행사 등으로 실제 결제 금액과 보증금 금액이 다를 수 있기에 미리 추가
    private BigDecimal depositAmount;       
    private BigDecimal repayAmount;
    private BigDecimal penaltyAmount;

    public static UserDepositHistoryEntity from(SessionInfo sessionInfo, ChallengeDto.ChallengeParticipateRequest request, ChallengeEntity challenge) {
        return UserDepositHistoryEntity.builder()
                .userId(sessionInfo.getUserId())
                .challengeId(challenge.getId())
                .payAmount(request.getDeposit())
                .depositAmount(request.getDeposit())
                .build();
    }
}
