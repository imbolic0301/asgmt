package com.partimestudy.domain.deposit.persistence;

import com.partimestudy.domain.challenge.web.dto.ChallengeDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    // 보증 이력 목록 조회
    // TODO 검색 조건 추가
    @Select("""
            SELECT
            	udh.challenge_id AS challengeId
            	, c.title AS challengeName
                , udh.deposit_amount AS depositAmount
                , udh.pay_amount AS payAmount
                , IF(udh.status = 1, 'ACTIVE', 'DEACITVE') AS status
            FROM user_deposit_history udh
            	INNER JOIN challenge c ON udh.challenge_id = c.id
            WHERE user_id = #{userId}
            LIMIT #{pageRequest.offset}, #{pageRequest.showCount}
            """)
    List<ChallengeDto.MyChallengeInfo> findByConditions(Integer userId, ChallengeDto.MyChallengePageRequest pageRequest);

    // 보증 이력 목록 전체 개수 조회
    // TODO 검색 조건 추가
    @Select("""
            SELECT IFNULL(COUNT(*), 0)
            FROM user_deposit_history
            WHERE user_id = #{userId}
            """)
    Integer countByConditions(Integer userId);

}
