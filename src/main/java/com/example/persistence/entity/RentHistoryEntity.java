package com.example.persistence.entity;

import com.example.web.dto.RentDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 대여 기록 entity
 */
//@Table("rent_history")
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RentHistoryEntity {
    private Long seq;
    private Long bookId;
    private String title;
    private Integer userId;
    private Integer price;

    public static RentHistoryEntity from(RentDto.BookResponse book, Integer userId) {
        return RentHistoryEntity.builder()
                .bookId(book.getId())
                .userId(userId)
                .title(book.getTitle())
                .price(book.getPrice())
                .build();
    }
}