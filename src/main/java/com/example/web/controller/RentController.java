package com.example.web.controller;

import com.example.web.dto.CommonDto;
import org.springframework.http.ResponseEntity;

public class RentController {

    // 도서 대여하기
    public ResponseEntity<?> rentBook() {
        return CommonDto.emptyResponse();
    }

    // 도서 목록 조회
    public ResponseEntity<?> findRentalBooks() {
        return CommonDto.responseFrom("");
    }

}
