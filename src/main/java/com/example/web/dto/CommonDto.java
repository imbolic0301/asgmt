package com.example.web.dto;

import com.example.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * 공용 Response Body
 */
public class CommonDto {

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
        return ResponseEntity.status(exception.getResultStatus()).body(ErrorResponse.from(exception));
    }

    /**
     * 정상 호출에 대해, 데이터가 있는 리스트형의 응답을 생성한다.
     */
    public static <T> ResponseEntity<ListResponse<T>> responseFrom(List<T> data, PageRequest pageRequest) {
        return ResponseEntity.ok(ListResponse.from(data, pageRequest));
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

    // 공용 JSON body 를 만들기 위한 내부 데이터 클래스
    @ToString
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ListResponse<T> {
        private final List<T> list;
        private final PageInfo pageInfo;

        public static <T> ListResponse<T> from(List<T> list, PageRequest pageRequest) {
            return ListResponse.<T>builder()
                    .list(list)
                    .pageInfo(
                        PageInfo.builder()
                                .cursorPage(pageRequest.getCursorPage())
                                .pageSize(pageRequest.getPageSize())
                                .totalCount(
                                        (pageRequest.getTotalCount() != null)
                                                ? pageRequest.getTotalCount()
                                                : list.size()
                                )
                                .build()
                    )
                    .build();
        }

    }

    /**
     * 조회 API 에서 페이징 관련 변수를 관리하기 위한 인터페이스
     */
    public interface PageRequest {
        Integer getCursorPage();
        Integer getPageSize();

        // LIMIT 쿼리나 CDP API 의 파라미터로 쓰이는 offset 의 계산식
        default Integer getOffset() {
            return (getCursorPage()-1) * getPageSize();
        }
        // CDP API 의 파라미터로 쓰이는 limit 의 계산식
        default Integer getLimit() {
            return getPageSize();
        }
        // totalCount 는 선택적으로 구현한다.
        default Long getTotalCount() { return 0L; }
    }

    @Getter
    @ToString
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class PageInfo {
        private Long totalCount;
        private Integer cursorPage;
        private Integer pageSize;
    }

    @Builder
    @Getter
    public static class ErrorResponse {
        private final String errorCode;
        private final String errorMessage;

        public static ErrorResponse from(CustomException exception) {
            return ErrorResponse.builder()
                    .errorCode(exception.getErrorCode())
                    .errorMessage(exception.getErrorMessage())
                    .build();
        }
    }

}
