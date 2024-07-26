package com.pard.admlong_be.domain.user.service;

import com.pard.admlong_be.domain.user.dto.request.UserRequestDTO;
import com.pard.admlong_be.domain.user.entity.User;
import com.pard.admlong_be.global.responses.error.exceptions.ProjectException;
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

    public ResponseDTO login(UserRequestDTO.Login dto, HttpServletResponse response) {
        try {
            String email = dto.getEmail();
            User user = userService.login(email);

            //JWT 토큰 생성 로직
//            String token = jWTUtil.createJwt(responseDTO.getResponse_object().)
        } catch (ProjectException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(false, "로그인 과정에서 UserFacade단에 처리되지 않은 문제가 발생했습니다.");
        }

        return new ResponseDTO(true, "nothing happened");
    }
}
