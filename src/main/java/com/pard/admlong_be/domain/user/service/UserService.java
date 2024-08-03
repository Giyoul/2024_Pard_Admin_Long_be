package com.pard.admlong_be.domain.user.service;

import com.pard.admlong_be.domain.user.dto.request.UserRequestDTO;
import com.pard.admlong_be.domain.user.dto.response.UserResponseDTO;
import com.pard.admlong_be.domain.user.entity.User;
import com.pard.admlong_be.domain.user.repository.UserRepository;
import com.pard.admlong_be.global.responses.error.exceptions.ProjectException;
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
}
