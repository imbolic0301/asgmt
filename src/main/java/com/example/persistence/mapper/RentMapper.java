package com.example.persistence.mapper;

import com.example.persistence.entity.BookEntity;
import com.example.persistence.entity.ConsignmentHistoryEntity;
import com.example.persistence.entity.RentHistoryEntity;
import com.example.web.dto.RentDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RentMapper {
    // 대여 가능한 책 조회
    @Select("""
            """)
    List<BookEntity> findAvailableBooks(Integer orderType);
    // 대여할 책 조회
    @Select("""
            SELECT
                b.id AS id
                , b.title
                , b.isbn
                , b.price
                , u.name AS userName
            FROM book_info b
                INNER JOIN user_info u ON b.user_id = u.id
            WHERE 
                b.id = #{bookId} AND b.is_del = 0 AND b.is_rentable = 1
            """)
    RentDto.BookResponse findAvailableBook(Long bookId);
    // 대여 처리
    void rent(Integer userId, Long bookId);
    // 대여 기록 생성
    @Options(useGeneratedKeys = true, keyProperty = "seq")
    @Insert(
            """
                INSERT INTO rent_history (
                    user_id
                    , book_id
                    , title
                    , price
                    , created_datetime
                ) VALUES (
                    #{userId}
                    , #{bookId}
                    , #{title}
                    , #{price}
                    , now()
                )
            """
    )
    void createHistory(RentHistoryEntity entity);
    // 대여 가능 상태 변경 - 일정 시간 후 자동 반납 처리 용도
    @Update("""
                UPDATE book_info
                SET is_rentable = #{isRentable}
                WHERE id = #{bookId}
            """)
    void changeRentalStatus(Long bookId, Boolean isRentable);
}
