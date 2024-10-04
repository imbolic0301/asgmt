package com.partimestudy.global.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.partimestudy.global.web.exception.CustomException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ListResponse<T> {
        private final PageInfo pageInfo;
        private final List<T> list;

        public static <T> ListResponse<T> from(List<T> list, PageInfo pageInfo) {
            return ListResponse.<T>builder()
                    .list(list)
                    .pageInfo(pageInfo)
                    .build();
        }

        public static <T> ListResponse<T> from(List<T> list) {
            return ListResponse.<T>builder()
                    .list(list)
                    .pageInfo(null)
                    .build();
        }

    }

    @Builder
    @Getter
    public static class ErrorResponse {
        private final String errorCode;
        private final String errorMessage;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String externalError;

        public static ErrorResponse from(CustomException exception) {
            return ErrorResponse.builder()
                    .errorCode(exception.getErrorCode())
                    .errorMessage(exception.getErrorMessage())
                    .externalError(exception.getExternalError())
                    .build();
        }
    }

    @Builder
    @Schema(description = "페이징 조회 요청 공통 파라미터")
    public record PageRequest(
            @Schema(description = "현재 페이지", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
            int page
            , @Schema(description = "페이지당 조회 개수", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
            int showCount
    ) {
        public static PageRequest empty(Integer page, Integer showCount) {
            return PageRequest.builder()
                    .page(page)
                    .showCount(showCount)
                    .build();
        }

        public int page() {
            return (this.page < 1) ? 1 : this.page;
        }

        public int showCount() { return (this.showCount < 10) ? 10 : this.showCount; }

        public int offset() {
            return (this.page() - 1) * this.showCount();
        }
    }

    @Builder
    @Schema(description = "페이징 조회 응답 공통 파라미터")
    public record PageInfo(
            @Schema(description = "요소 전체 개수", example = "25", type = "long") long totalCount
            , @Schema(description = "현재 페이지", example = "1", type = "Integer") int page
            , @Schema(description = "페이지당 조회 개수", example = "10", type = "Integer") int showCount
    ) {
        public static PageInfo empty(Integer page, Integer showCount) {
            return PageInfo.builder()
                    .totalCount(0).page(page).showCount(showCount)
                    .build();
        }
    }

}
