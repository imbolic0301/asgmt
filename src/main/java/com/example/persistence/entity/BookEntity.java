package com.example.persistence.entity;

import com.example.web.dto.ConsignmentDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 책 정보 entity
 */
//@Table("book_info")
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookEntity {
    private Long id;
    private Integer userId;
    private String title;
    private String isbn;
    private Integer price;

    public static BookEntity fromValidated(ConsignmentDto.Request request) {
        return BookEntity.builder()
                .userId(request.getUserId())
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .price(request.getPrice())
                .build();
    }
}
