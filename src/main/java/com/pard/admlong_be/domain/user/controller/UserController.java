package com.pard.admlong_be.domain.user.controller;

//import com.pard.admlong_be.domain.user.dto.request.UserRequestDTO;
//import com.pard.admlong_be.domain.user.service.UserFacade;
import com.pard.admlong_be.global.util.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
//    private final UserFacade userFacade;
//
//    @PostMapping("/login")
//    @Operation(summary = "이메일을 입력하고 토큰을 받아옵니다.", description = "유저의 이메일을 입력하면, 해당 유저의 토큰을 발급해줍니다.")
//    public ResponseEntity<ResponseDTO> login(@RequestBody UserRequestDTO.Login request, HttpServletResponse response) {
//        ResponseDTO responseDTO = userFacade.login(request, response);
//        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(responseDTO);
//    }

    // 위 코드는 로그인이지 회원가입이 아님. 회원가입은 따로 구현해줘야 하는 것이 맞음.
}