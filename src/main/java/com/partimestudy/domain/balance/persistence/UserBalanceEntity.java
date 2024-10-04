package com.partimestudy.domain.balance.persistence;

import lombok.*;

import java.math.BigDecimal;

/**
 * 유저 현재 잔고 Entity
 */
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@Table("user_balance")
public class UserBalanceEntity {
    private Integer userId;
    private BigDecimal balance;
    private BigDecimal deposit;
}
