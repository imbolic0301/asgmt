package com.partimestudy.domain.balance.persistence;

import com.partimestudy.domain.user.persistence.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface UserBalanceMapper {

    @Select("""
            SELECT * FROM user_balance WHERE user_id = #{userId}
            """)
    UserBalanceEntity findCurrentBalanceBy(Integer userId);

    @Insert(
    """
    INSERT INTO user_balance (
        user_id
        , balance
        , deposit
    ) VALUES (
        #{id}
        , 0
        , 0
    )
    """)
    void initNewUserBalance(UserEntity entity);

    @Update("""
            UPDATE user_balance
            SET 
                balance = balance - #{amount}
                , deposit = deposit + #{amount}
            WHERE user_id = #{userId}
            """)
    void moveBalanceToDeposit(Integer userId, BigDecimal amount);
}
