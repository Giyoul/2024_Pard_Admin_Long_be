package com.pard.admlong_be.domain.user.service;

import com.pard.admlong_be.domain.user.dto.request.UserRequestDTO;
import com.pard.admlong_be.domain.user.dto.response.UserResponseDTO;
import com.pard.admlong_be.domain.user.entity.User;
import com.pard.admlong_be.domain.user.repository.UserRepository;
import com.pard.admlong_be.global.responses.error.exceptions.ProjectException;
import com.pard.admlong_be.global.security.jwt.JWTUtil;
import com.pard.admlong_be.global.util.ResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public User login(String email) throws ProjectException.UserFacadeException {
        if (!userRepository.existsByEmail(email)) {
            throw new ProjectException.UserNotExistException("회원가입 되어있지 않은 유저입니다.");
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ProjectException.UserNotFoundException("해당 유저는 존재하나, Repository에서 불러오는 과정에서 문제가 발생했습니다."));
        return user;
    }

    @Transactional
    public User register(UserRequestDTO.Register request) throws ProjectException.UserFacadeException {
        if(request.getGender() == 1 || request.getGender() == 0) {
            User user = User.builder()
                    .name(request.getName())
                    .birthday(request.getBirthday())
                    .email(request.getEmail())
                    .gender(request.getGender())
                    .height(request.getHeight())
                    .weight(request.getWeight())
                    .blood_type(request.getBlood_type())
                    .last_donation_date(request.getLast_donation_date())
                    .build();
            userRepository.save(user);
            return user;
        } else {
            throw new ProjectException.InvalidValueException("gender must be 1 or 0");
        }
    }

    public ResponseDTO getUserByToken(String token) {
        try {
            if (!userRepository.existsByEmail(jwtUtil.getEmail(token))) {
                throw new ProjectException.UserNotExistException("없는 유저입니다.");
            }
            User user = userRepository.findByEmail(jwtUtil.getEmail(token)).orElseThrow(() -> new ProjectException.UserNotFoundException("해당 유저는 존재하나, Repository에서 불러오는 과정에서 문제가 발생했습니다."));
            UserResponseDTO.GetUserResponseDTO response = new UserResponseDTO.GetUserResponseDTO(user);
            return new ResponseDTO(true, "유저 정보 탐색 성공", response);
        } catch (ProjectException.UserNotFoundException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(false, "유저 서칭 과정에서 token으로 검색 중 처리되지 않은 오류가 발생했습니다.", e);
        }
    }
}
