package com.partimestudy.domain.balance.persistence;

import com.partimestudy.domain.user.persistence.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserBalanceMapper {

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
}
