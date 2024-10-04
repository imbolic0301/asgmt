package com.partimestudy.domain.challenge.persistence.entity;

import lombok.*;

import java.math.BigDecimal;

/**
 * 챌린지 Entity
 */
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@Table("challenge")
public class ChallengeEntity {
    private Long id;
    private String title;
    private BigDecimal depositMin;
    private BigDecimal depositMax;
}
