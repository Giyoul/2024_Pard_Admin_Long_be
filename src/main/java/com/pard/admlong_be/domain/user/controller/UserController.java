package com.pard.admlong_be.domain.user.controller;

import com.pard.admlong_be.domain.user.dto.request.UserRequestDTO;
import com.pard.admlong_be.domain.user.dto.response.UserResponseDTO;
import com.pard.admlong_be.domain.user.service.UserFacade;
import com.pard.admlong_be.domain.user.service.UserService;
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
    private final UserService userService;

    @GetMapping("/login")
    @Operation(summary = "이메일을 입력하고 토큰을 받아옵니다.", description = "유저의 이메일을 입력하면, 해당 유저의 토큰을 발급해줍니다.")
    public ResponseEntity<ResponseDTO> login(@RequestParam String email, HttpServletResponse response) {
        ResponseDTO responseDTO = userFacade.login(email, response);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(responseDTO);
    }

    @PostMapping("/register")
    @Operation(summary = "정보들을 보내주고, 회원 가입을 진행합니다.", description = "유저의 정보를 입력하면, 해당 유저의 정보를 저장하고 토큰을 발급해줍니다.")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserRequestDTO.Register request, HttpServletResponse response) {
        ResponseDTO responseDTO = userFacade.register(request, response);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(responseDTO);
    }

    @GetMapping("/user")
    @Operation(summary = "쿠키 값을 보내주고, 해당 쿠키의 유저 정보를 가져옵니다.", description = "쿠키를 보내면, 챌린지와 사용자 등 유저 정보를 불러옵니다.")
    public ResponseEntity<ResponseDTO> getUser(@CookieValue(value = "Authorization") String token) {
        ResponseDTO responseDTO = userService.getUserByToken(token);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(responseDTO);
    }

    @GetMapping("/user/duedate")
    @Operation(summary = "쿠키 값을 보내주고, 해당 쿠키 유저의 헌혈가능일까지 남은 날짜를 가져옵니다.", description = "현재 이런 저런 이슈로, ")
    public ResponseEntity<ResponseDTO> getDueDate(@CookieValue(value = "Authorization") String token) {
        ResponseDTO responseDTO = userService.getDueDateByToken(token);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(responseDTO);
    }
}
