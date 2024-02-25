package com.example.persistence.mapper;

import com.example.persistence.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 신규 회원 추가
    void createNewUser(UserEntity entity);
    // 이메일로 회원 찾기
    UserEntity findByEmail(String email);
}
