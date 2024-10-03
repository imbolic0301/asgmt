package com.partimestudy.swagger.challenge;


import com.partimestudy.domain.challenge.web.dto.ChallengeDto;
import com.partimestudy.global.web.dto.CommonDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class ChallengeSwaggerDto {

    @ToString
    @Getter
    @Schema(description = "챌린지 목록 조회 응답 예시")
    public static class ChallengeListResponse {
        @Schema(description = "응답 예시", implementation = ChallengeListExample.class)
        private ChallengeListExample data;
    }

    @ToString
    @Getter
    @Schema(description = "챌린지 목록 조회 응답 예시")
    public static class ChallengeListExample {
        @Schema(description = "페이지 정보", implementation = CommonDto.PageInfo.class)
        private CommonDto.PageInfo pageInfo;
        @Schema(description = "챌린지 목록")
        private List<ChallengeDto.ChallengeElement> list;
    }


}