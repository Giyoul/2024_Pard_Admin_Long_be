package com.pard.admlong_be.domain.user.controller;

import com.pard.admlong_be.domain.user.dto.request.UserRequestDTO;
import com.pard.admlong_be.domain.user.service.UserFacade;
import com.pard.admlong_be.global.util.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserFacade userFacade;

    @GetMapping("/login")
    @Operation(summary = "이메일을 입력하고 토큰을 받아옵니다.", description = "유저의 이메일을 입력하면, 해당 유저의 토큰을 발급해줍니다.")
    public ResponseEntity<ResponseDTO> login(@RequestParam String email, HttpServletResponse response) {
        ResponseDTO responseDTO = userFacade.login(email, response);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(responseDTO);
    }
}
