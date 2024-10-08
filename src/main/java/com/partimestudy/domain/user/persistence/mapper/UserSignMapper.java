package com.partimestudy.domain.user.persistence.mapper;

import com.partimestudy.domain.user.persistence.entity.UserEntity;
import com.partimestudy.domain.user.web.dto.UserSignDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserSignMapper {
    
    // 회원 가입으로 유저 생성
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("""
            INSERT INTO user (
                login_id
                , user_name
                , password
            ) VALUES (
                #{loginId}
                , #{userName}
                , #{password}
            )
            """)
    void createUserBy(UserEntity entity);

    // 로그인 아이디 중복 확인
    @Select("""
            SELECT TRUE FROM user WHERE login_id = #{loginId} AND is_del = 0
            """)
    Boolean isDuplicatedLoginId(String loginId);

    // 로그인 정보로 현재 활성화된 회원 생성
    @Select("""
            SELECT 
                * 
            FROM user 
            WHERE login_id = #{loginId} AND password = #{password} AND is_del = 0
            """)
    UserEntity findUserBy(UserSignDto.SignInRequest request);
    
}
