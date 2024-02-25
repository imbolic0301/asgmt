package com.example.persistence.mapper;

import com.example.persistence.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 신규 회원 추가
    @Insert("""
            INSERT INTO user_info (
                name
                , email
                , phone
                , pwd
                , created_datetime
            ) VALUES ( 
                #{name}
                , #{email} 
                , #{phone} 
                , #{password} 
                , now()
            )
            """)
    void createNewUser(UserEntity entity);

    // 이메일로 회원 찾기
    @Select("""
            SELECT 
                *
            FROM 
                user_info
            WHERE 
                email = #{email} AND IS_DEL = 0
            """)
    UserEntity findByEmail(String email);
}
