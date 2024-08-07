package com.pard.admlong_be.domain.user.service;

import com.pard.admlong_be.domain.challenge.dto.response.ChallengeResponseDTO;
import com.pard.admlong_be.domain.challenge.entity.Challenge;
import com.pard.admlong_be.domain.user.dto.request.UserRequestDTO;
import com.pard.admlong_be.domain.user.dto.response.UserResponseDTO;
import com.pard.admlong_be.domain.user.entity.User;
import com.pard.admlong_be.domain.user.repository.UserRepository;
import com.pard.admlong_be.domain.userChallengeRelation.entity.UserChallengeRelation;
import com.pard.admlong_be.global.responses.error.exceptions.ProjectException;
import com.pard.admlong_be.global.security.jwt.JWTUtil;
import com.pard.admlong_be.global.util.ResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
            List<Challenge> challengeList = user.getUserChallengeRelationList().stream()
                    .map(UserChallengeRelation::getChallenge)
                    .toList();
            UserResponseDTO.GetUserResponseDTO response;
            if (challengeList.isEmpty()) {
                response = new UserResponseDTO.GetUserResponseDTO(user);
            } else {
                List<ChallengeResponseDTO.GetChallengeResponse> userChallenge = challengeList.stream()
                        .map(ChallengeResponseDTO.GetChallengeResponse::new)
                        .toList();
                response = new UserResponseDTO.GetUserResponseDTO(user, userChallenge);
            }
            return new ResponseDTO(true, "유저 정보 탐색 성공", response);
        } catch (ProjectException.ChallengeNotExistException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (ProjectException.UserNotFoundException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (ProjectException.UserNotExistException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(false, "유저 서칭 과정에서 token으로 검색 중 처리되지 않은 오류가 발생했습니다. -> " + e.getMessage());
        }
    }

    public ResponseDTO getDueDateByToken(String token) {
        try {
            if (!userRepository.existsByEmail(jwtUtil.getEmail(token))) {
                throw new ProjectException.UserNotExistException("없는 유저입니다.");
            }
            User user = userRepository.findByEmail(jwtUtil.getEmail(token)).orElseThrow(()
                    -> new ProjectException.UserNotFoundException("해당 유저는 존재하나, Repository에서 불러오는 과정에서 문제가 발생했습니다."));
            Date lastDonationDate = user.getLast_donation_date();
            // 현재 날짜 가져오기
            LocalDate currentDate = LocalDate.now();
            // java.util.Date를 java.time.LocalDate로 변환
            LocalDate lastDonationLocalDate = lastDonationDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            // 두 날짜 간의 차이 계산
            long daysBetween = ChronoUnit.DAYS.between(lastDonationLocalDate, currentDate);

            Integer donation_type = null;
            if (user.getBloodDonationList() != null && !user.getBloodDonationList().isEmpty()) {
                donation_type = user.getBloodDonationList()
                        .get(user.getBloodDonationList().size() - 1)
                        .getDonation_type();
            } else {
                // 리스트가 비어있거나 null일 경우의 처리
                // 예: donation_type을 기본값으로 설정하거나 다른 처리를 할 수 있습니다.
                // donation_type = 0; // 기본값으로 설정 (필요에 따라 설정)
                return new ResponseDTO(true, "헌혈 내역이 없습니다.");
            }
            if (donation_type == 0) {
                daysBetween = 62-daysBetween;
            }
            if (donation_type == 1) {
                daysBetween = 14-daysBetween;
            }
            if (donation_type == 2) {
                daysBetween = 14-daysBetween;
            }
            // 이전 헌혈일로부터 지난 시간입니다.
            return new ResponseDTO(true, "이전 헌혈 불러오는데 성공했습니다.", new UserResponseDTO.GetDueDateResponseDTO(daysBetween));
        } catch (ProjectException.UserNotFoundException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (ProjectException.UserNotExistException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(false, "유저 헌혈일 duedate 탐색 중 처리되지 않은 오류가 발생했습니다.\n" + e.getMessage());
        }
    }
}
