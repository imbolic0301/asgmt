package com.example.web.dto;

import com.example.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;

import java.util.List;
import java.util.Map;

/**
 * 공용 Response Body
 */
public class ResponseDto {

    /**
     * 정상 호출에 대해, 데이터가 있는 공용 응답을 생성한다.
     * @param data : API 응답 결과로 리턴할 객체 (DTO Response 등)
     */
    public static <T> ResponseEntity<Response<T>> responseFrom(T data) {
        return ResponseEntity.ok(Response.from(data));
    }

    /**
     * 정상 호출에 대해, 데이터가 없는 공용 응답을 생성한다.
     */
    public static ResponseEntity<?> emptyResponse() {
        return ResponseEntity.ok(Response.from(null));
    }

    /**
     * 커스텀 예외로부터 오류 관련 공용 응답을 생성한다.
     */
    public static ResponseEntity<?> exceptionResponseFrom(CustomException exception) {
        return ResponseEntity.status(exception.getResultStatus()).body(exception.getErrorMessage());
    }


    // 공용 JSON body 를 만들기 위한 내부 데이터 클래스
    @ToString
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Response<T> {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final T data;
        private static <T> Response<T> from(T data) {
            return Response.<T>builder()
                    .data(data)
                    .build();
        }

    }

}
