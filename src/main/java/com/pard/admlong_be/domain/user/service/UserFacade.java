package com.pard.admlong_be.domain.user.service;

import com.pard.admlong_be.domain.user.dto.request.UserRequestDTO;
import com.pard.admlong_be.domain.user.entity.User;
import com.pard.admlong_be.global.responses.error.exceptions.ProjectException;
import com.pard.admlong_be.global.security.cookie.service.CookieService;
import com.pard.admlong_be.global.security.jwt.JWTUtil;
import com.pard.admlong_be.global.util.ResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final JWTUtil jWTUtil;
    private final CookieService cookieService;

    public ResponseDTO login(UserRequestDTO.Login dto, HttpServletResponse response) {
        try {
            System.err.println("일단 여기까지는 왔나?0");
            String email = dto.getEmail();
            User user = userService.login(email);
            System.err.println("일단 여기까지는 왔나?1");
            //JWT 토큰 생성 로직
            String token = jWTUtil.createJwt(user.getName(), user.getEmail());
            System.err.println("일단 여기까지는 왔나?2");
            response.addCookie(cookieService.createCookie("Authorization", token));
            System.err.println("일단 여기까지는 왔나?3");
            return new ResponseDTO(true, "토큰 생성 성공!", token);
        } catch (ProjectException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(false, "로그인 과정에서 UserFacade단에 처리되지 않은 문제가 발생했습니다.");
        }
    }
}
