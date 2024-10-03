package com.partimestudy.domain.challenge.web.dto;

import com.partimestudy.domain.challenge.persistence.entity.ChallengeEntity;
import com.partimestudy.global.web.exception.CustomExceptionTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ChallengeDto {


    @Getter
    @Setter
    @Builder(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Schema(description = "챌린지 목록 조회 요청 파라미터")
    public static class ChallengePageRequest {
        @Schema(description = "현재 페이지", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Integer page;
        @Schema(description = "페이지당 조회 개수", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private Integer showCount;
//        @Schema(description = "제목 검색어", example = "목요일", requiredMode = Schema.RequiredMode.REQUIRED)
//        private String challengeTitle;

        public void init() {
            this.page = (this.page == null || this.page < 1) ? 1 : this.page;
            this.showCount = (this.showCount == null || this.showCount < 1) ? 10 : this.showCount;
        }

        public Integer getOffset() {
            return (this.page - 1) * this.showCount;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    @Schema(description = "챌린지 도전 신청")
    public static class ChallengeParticipateRequest {
        @Schema(description = "챌린지 아이디", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long challengeId;
        @Schema(description = "챌린지 이름", example = "원하는 일정으로 공부하기", requiredMode = Schema.RequiredMode.REQUIRED)
        private String challengeName;
        @Schema(description = "보증금", example = "123.0", requiredMode = Schema.RequiredMode.REQUIRED)
        private BigDecimal deposit;
        @Schema(description = "챌린지 일정", requiredMode = Schema.RequiredMode.REQUIRED, implementation = ChallengeScheduleInfo.class)
        private List<ChallengeScheduleInfo> challengeSchedules;

        public void validate() {
            // TODO - 세부 파라미터에 대한 검증 규칙 추가 필요
            if(this.challengeId == null
            || this.deposit == null
            || this.challengeSchedules == null || this.challengeSchedules.isEmpty())
                throw CustomExceptionTypes.INVALID_PARAMETER_FAILURE_MESSAGE.init("올바르지 않은 파라미터 양식입니다.");
        }

    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    @Schema(description = "챌린지 스케쥴 정보")
    public static class ChallengeScheduleInfo {
        @Schema(description = "챌린지 아이디", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDate applyDate;
        @Schema(description = "참여 시간", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer hour;

        public void validate() {
            // TODO - 세부 파라미터에 대한 검증 규칙 추가 필요
            if(this.applyDate == null
                    || this.applyDate.isBefore(LocalDate.now(ZoneId.of("+09:00")))
                    || hour == null
                    || 1 > this.hour
            )
                throw CustomExceptionTypes.INVALID_PARAMETER_FAILURE_MESSAGE.init("올바르지 않은 파라미터 양식입니다.");
        }
    }

    @Getter
    @ToString
    @Builder(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Schema(description = "챌린지 항목 정보")
    public static class ChallengeElement {
        @Schema(description = "챌린지 ID", example = "1")
        private Long challengeId;
        @Schema(description = "챌린지 이름", example = "원하는 일정으로 공부하기")
        private String challengeName;
        @Schema(description = "최소 챌린지 보증금", example = "10000.0")
        private BigDecimal depositMin;
        @Schema(description = "최대 챌린지 보증금", example = "100000.0")
        private BigDecimal depositMax;

        public static ChallengeElement from(ChallengeEntity entity) {
            return ChallengeElement.builder()
                    .challengeId(entity.getId())
                    .challengeName(entity.getTitle())
                    .depositMax(entity.getDepositMax())
                    .depositMin(entity.getDepositMin())
                    .build();
        }
    }

}
