package com.pard.admlong_be.domain.questionnaire.controller;


import com.pard.admlong_be.domain.questionnaire.dto.request.QuestionnaireRequestDTO;
import com.pard.admlong_be.domain.questionnaire.service.QuestionnaireService;
import com.pard.admlong_be.global.util.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    @PostMapping("")
    @Operation(summary = "로그인 없이 문진하기", description = "문진 정보를 보내면 헌혈이 가능한지를 판단해 불가하다면 이유를 반환해 줍니다.")
    public ResponseEntity<ResponseDTO> testWithoutLogin(@RequestBody QuestionnaireRequestDTO request) {
        ResponseDTO responseDTO = questionnaireService.testWithoutLogin(request);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(responseDTO);
    }


    @PostMapping("/login")
    @Operation(summary = "로그인후에 문진하기", description = "문진 정보를 보내면 헌혈이 가능한지를 판단해 불가하다면 이유를 반환해 줍니다.")
    public ResponseEntity<ResponseDTO> testWithoutLogin(@RequestBody QuestionnaireRequestDTO request, @CookieValue(value = "Authorization") String token) {
        ResponseDTO responseDTO = questionnaireService.testWithLogin(request, token);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(responseDTO);
    }


}
