package com.example.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class RentDto {

    @Getter
    @Setter
    @ToString
    public static class BookListRequest implements CommonDto.PageRequest {
        private Integer cursorPage = 1;
        private Integer pageSize = 20;
        private Integer orderType = 1;  // 1 : 대여 많은 순 | 2 : 낮은 가격 순 | 3 : 최근 등록 순
    }

}
