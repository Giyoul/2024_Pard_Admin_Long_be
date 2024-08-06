package com.pard.admlong_be.domain.bloodDonation.controller;

import com.pard.admlong_be.domain.bloodDonation.dto.request.BloodDonationRequestDTO;
import com.pard.admlong_be.domain.bloodDonation.service.BloodDonationService;
import com.pard.admlong_be.global.util.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/donation")
public class BloodDonationController {
    private final BloodDonationService bloodDonationService;

    @PostMapping("")
    @Operation(summary = "헌혈 인증", description = "보내준 정보로 헌혈 인증합니다.")
    public ResponseEntity<ResponseDTO> joinChallengeById(@CookieValue(value = "Authorization") String token, @RequestBody BloodDonationRequestDTO.BloodDonationCertifyRequestDTO request) {
        ResponseDTO responseDTO = bloodDonationService.certifyBloodDonation(token, request);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(responseDTO);
    }

}
