package com.partimestudy.domain.user.persistence.mapper;

import com.partimestudy.domain.user.persistence.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("""
            SELECT * FROM user WHERE id = #{userId} AND is_Deleted = 0
            """)
    UserEntity findUserById(Integer userId);
}
