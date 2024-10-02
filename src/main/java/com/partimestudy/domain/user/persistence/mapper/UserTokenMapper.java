package com.partimestudy.domain.user.persistence.mapper;

import com.partimestudy.domain.user.persistence.entity.UserTokenEntity;
import com.partimestudy.global.jwt.RefreshToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserTokenMapper {
    
    // accessToken 검증
    @Select("""
            SELECT TRUE 
            FROM user_token_history
            WHERE 
                user_id = #{userId} AND access_token = #{uuid} 
                AND is_del = 0 AND access_token_expire_at >= NOW()
            """)
    Boolean isAliveAccessToken(Integer userId, String uuid);

    // refreshToken 검증
    @Select("""
            SELECT TRUE 
            FROM user_token_history
            WHERE 
                user_id = #{userId} AND refresh_token = #{uuid} 
                AND is_del = 0 AND refresh_token_expire_at >= NOW()
            """)
    Boolean isAliveRefreshToken(RefreshToken refreshToken);

    // 토큰 발급 이력 생성
    @Select("""
            INSERT INTO user_token_history (
                user_id
                , access_token
                , access_token_expire_at
                , refresh_token
                , refresh_token_expire_at
                , created_at
                , is_del
            ) VALUES (
                #{userId}
                , #{accessToken}
                , #{accessTokenExpireAt}
                , #{refreshToken}
                , #{refreshTokenExpireAt}
                , NOW()
                , 0
            )
            """)
    void create(UserTokenEntity entity);

    // 갱신에 사용된 refreshToken 만료 처리
    @Update("""
            UPDATE user_token_history
            SET 
                is_del = 1
                , updated_at = NOW()
            WHERE refresh_token = #{uuid}  AND user_id = #{userId}
            """)
    void removeUsedToken(RefreshToken refreshToken);

}
