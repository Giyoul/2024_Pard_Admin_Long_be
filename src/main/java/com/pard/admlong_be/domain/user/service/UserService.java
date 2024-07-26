package com.pard.admlong_be.domain.user.service;

import com.pard.admlong_be.domain.user.dto.response.UserResponseDTO;
import com.pard.admlong_be.domain.user.entity.User;
import com.pard.admlong_be.domain.user.repository.UserRepository;
import com.pard.admlong_be.global.responses.error.exceptions.ProjectException;
import com.pard.admlong_be.global.util.ResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;

    public User login(String email) throws ProjectException.UserFacadeException {
        if (!userRepository.existsByEmail(email)) {
            throw new ProjectException.UserNotFoundException("해당 이메일의 유저를 찾지 못했습니다.");
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ProjectException.UserNotFoundException("해당 유저는 존재하나, Repository에서 불러오는 과정에서 문제가 발생했습니다."));
        return user;
    }
}
