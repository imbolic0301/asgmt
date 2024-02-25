package com.example.persistence.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 위탁 기록 entity
 */
//@Table("consignment_history")
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsignmentHistoryEntity {
    private Long seq;
    private Integer userId;
    private Long bookId;
    private String title;
    private String isbn;
    private Integer price;
}