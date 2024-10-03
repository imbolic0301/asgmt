package com.partimestudy.domain.challenge.web.controller;

import com.partimestudy.domain.challenge.service.ChallengeService;
import com.partimestudy.domain.challenge.web.dto.ChallengeDto;
import com.partimestudy.global.annotation.ResolvedParam;
import com.partimestudy.global.jwt.SessionInfo;
import com.partimestudy.global.web.dto.CommonDto;
import com.partimestudy.swagger.challenge.ChallengeSwaggerDto;
import com.partimestudy.swagger.common.CommonSwaggerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.service.RequestBodyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/challenges")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "챌린지", description = "챌린지 관련 API 입니다.")
public class ChallengeController {

    private final ChallengeService challengeService;


    @Operation(summary = "챌린지 목록 조회", description = "챌린지 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ChallengeSwaggerDto.ChallengeListResponse.class)))
    })
    @GetMapping
    public ResponseEntity<?> getChallenges(@ParameterObject ChallengeDto.ChallengePageRequest request,
                                           @ResolvedParam SessionInfo sessionInfo) {
        request.init();
        var responseBody = challengeService.findChallengesByConditions(request);
        return CommonDto.responseFrom(responseBody);
    }


    @Operation(summary = "챌린지 참여 신청", description = "챌린지 참여 신청을 처리합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CommonSwaggerDto.EmptyBodyExample.class)))
    })
    @PostMapping
    public ResponseEntity<?> participateChallenge(@ResolvedParam SessionInfo sessionInfo
            , @RequestBody ChallengeDto.ChallengeParticipateRequest request) {
        request.validate();
        challengeService.participateChallenge(sessionInfo, request);
        return CommonDto.emptyResponse();
    }

    // TODO - 챌린지 취소 - 시작 전에만 환불 가능하게 처리
    public void cancelChallenge() {

    }

}
