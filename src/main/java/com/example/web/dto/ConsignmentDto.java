package com.example.web.dto;

public class ConsignmentDto {
    // TODO - 추후 입력값 검증 필드 추가 필요
    public record Request(String title, String isbn, Integer price) {}
}
