package com.example.web.controller;

import com.example.web.dto.CommonDto;
import org.springframework.http.ResponseEntity;

public class ConsignmentController {

    // 책 위탁하기
    public ResponseEntity<?> consignmentBook() {
        return CommonDto.emptyResponse();
    }

}
