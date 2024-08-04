package com.pard.admlong_be.domain.challenge.controller;

import com.pard.admlong_be.domain.challenge.dto.request.ChallengeRequestDTO;
import com.pard.admlong_be.domain.challenge.service.ChallengeService;
import com.pard.admlong_be.global.util.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChallengeController {
    private final ChallengeService challengeService;

    @PostMapping("/challenge/create")
    @Operation(summary = "입력한 정보의 챌린지를 생성합니다.", description = "보내준 정보의 챌린지를 생성합니다.")
    public ResponseEntity<ResponseDTO>  createChallenge(@RequestBody ChallengeRequestDTO.createChallengeRequestDTO request) {
        ResponseDTO responseDTO = challengeService.createChallenge(request);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(responseDTO);
    }
}
