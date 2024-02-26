package com.example.web.controller;

import com.example.service.ConsignmentService;
import com.example.web.dto.CommonDto;
import com.example.web.dto.ConsignmentDto;
import com.example.web.dto.RentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final ConsignmentService consignmentService;


    // 책 위탁하기
    @PostMapping("/consignments")
    public ResponseEntity<?> consignmentBook(@RequestBody ConsignmentDto.Request request) {
        // TODO 세션 구현
        request.initUserId(1);
        request.validate();
        consignmentService.consignBook(request);
        return CommonDto.emptyResponse();
    }

    // 도서 대여하기
    @PostMapping("/{bookId}")
    public ResponseEntity<?> rentBook(@PathVariable(name = "bookId") Long bookId) {
        // TODO 세션 구현
       Integer userId = 1;

        return CommonDto.emptyResponse();
    }

    // 도서 목록 조회
    @GetMapping
    public ResponseEntity<?> findRentalBooks(RentDto.BookListRequest request) {
        return CommonDto.responseFrom("");
    }

}
