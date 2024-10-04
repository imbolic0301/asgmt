package com.partimestudy.domain.deposit.persistence;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DepositMapper {

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("""
            INSERT INTO user_deposit_history (
                user_id
                , challenge_id
                , pay_amount
                , deposit_amount
                , repay_amount
                , penalty_amount
                , status
                , created_at
            ) VALUES (
                #{userId}
                , #{challengeId}
                , #{payAmount}
                , #{depositAmount}
                , 0
                , 0
                , 1
                , now()
            )
            """)
    void createHistory(UserDepositHistoryEntity history);

    @Select("""
            SELECT TRUE
            FROM user_deposit_history
            WHERE user_id = #{userId} AND challenge_id = #{challengeId} AND status = 1
            """)
    Boolean findExistActiveDeposit(Integer userId, Long challengeId);
}
