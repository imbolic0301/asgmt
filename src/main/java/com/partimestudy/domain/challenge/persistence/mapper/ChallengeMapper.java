package com.partimestudy.domain.challenge.persistence.mapper;

import com.partimestudy.domain.challenge.persistence.entity.ChallengeEntity;
import com.partimestudy.domain.challenge.persistence.entity.UserChallengeScheduleEntity;
import com.partimestudy.domain.challenge.web.dto.ChallengeDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChallengeMapper {

    // 챌린지 상세 조회
    @Select("""
            SELECT *
            FROM challenge
            WHERE id = #{challnegeId} AND status = 'ACTIVE'
            """)
    ChallengeEntity findById(Long challengeId);

    // 챌린지 목록 조회
    // TODO 검색 조건 추가
    @Select("""
            SELECT *
            FROM challenge
            WHERE status = 'ACTIVE'
            ORDER BY created_at DESC
            LIMIT #{offset}, #{showCount}
            """)
    List<ChallengeEntity> findByConditions(ChallengeDto.ChallengePageRequest pageRequest);

    // 챌린지 목록 전체 개수 조회
    // TODO 검색 조건 추가
    @Select("""
            SELECT IFNULL(COUNT(*), 0)
            FROM challenge
            WHERE status = 'ACTIVE'
            """)
    Integer countByConditions();
    
    // 챌린지 목록 저장
    @Insert("""
            <script>
            INSERT INTO user_challenge_schedule (
                challenge_id
                , deposit_id
                , schedule_date
                , hour
                , created_at
            ) VALUES 
            <foreach collection="scheduleEntities" item="e" separator=","> (
                #{e.challengeId}
                , #{e.depositId}
                , #{e.scheduleDate}
                , #{e.hour}
                , NOW()
            )</foreach>
            </script>
            """)
    void createSchedules(List<UserChallengeScheduleEntity> scheduleEntities);
}
