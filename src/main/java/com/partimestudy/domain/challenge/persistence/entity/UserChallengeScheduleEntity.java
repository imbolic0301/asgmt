package com.partimestudy.domain.challenge.persistence.entity;

import com.partimestudy.domain.challenge.web.dto.ChallengeDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 챌린지 Entity
 */
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@Table("user_challenge_schedule")
public class UserChallengeScheduleEntity {
    private Long id;
    private Long challengeId;
    private Long depositId;
    private LocalDate scheduleDate;
    private Integer hour;

    public static UserChallengeScheduleEntity from(Long depositId, ChallengeEntity challenge, ChallengeDto.ChallengeScheduleInfo dto) {
        // 입력값에 대한 검증은 request 에서 사전에 처리하기 때문에, 형변환만 처리한다.
        return UserChallengeScheduleEntity.builder()
                .depositId(depositId)
                .challengeId(challenge.getId())
                .scheduleDate(dto.getApplyDate())
                .hour(dto.getHour())
                .build();
    }
}
