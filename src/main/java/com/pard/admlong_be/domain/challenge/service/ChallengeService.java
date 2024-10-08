package com.pard.admlong_be.domain.challenge.service;

import com.pard.admlong_be.domain.challenge.dto.request.ChallengeRequestDTO;
import com.pard.admlong_be.domain.challenge.dto.response.ChallengeResponseDTO;
import com.pard.admlong_be.domain.challenge.entity.Challenge;
import com.pard.admlong_be.domain.challenge.repository.ChallengeRepository;
import com.pard.admlong_be.domain.challengelike.entity.ChallengeLike;
import com.pard.admlong_be.domain.challengelike.repository.ChallengeLikeRepository;
import com.pard.admlong_be.domain.user.entity.User;
import com.pard.admlong_be.domain.user.repository.UserRepository;
import com.pard.admlong_be.domain.user.service.UserService;
import com.pard.admlong_be.domain.userChallengeRelation.entity.UserChallengeRelation;
import com.pard.admlong_be.domain.userChallengeRelation.repository.UserChallengeRelationRepository;
import com.pard.admlong_be.global.responses.error.exceptions.ProjectException;
import com.pard.admlong_be.global.security.jwt.JWTUtil;
import com.pard.admlong_be.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import org.springframework.scheduling.annotation.Scheduled;


@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeLikeRepository challengeLikeRepository;
    private final UserChallengeRelationRepository userChallengeRelationRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Transactional
    public ResponseDTO createChallenge(String token, ChallengeRequestDTO.createChallengeRequestDTO request) {
        try {
            Challenge challenge = new Challenge();
            Boolean challenge_finished = challengeFinished(request);
            challenge.createChallenge(request, challenge_finished);
            if (!userRepository.existsByEmail(jwtUtil.getEmail(token))) {
                throw new ProjectException.UserNotExistException("없는 유저입니다.");
            }
            User user = userRepository.findByEmail(jwtUtil.getEmail(token)).orElseThrow(() -> new ProjectException.UserNotFoundException("해당 유저는 존재하나, Repository에서 불러오는 과정에서 문제가 발생했습니다."));
            UserChallengeRelation userChallengeRelation = new UserChallengeRelation(user, challenge);
            userChallengeRelationRepository.save(userChallengeRelation);
            challengeRepository.save(challenge);
            return new ResponseDTO(true, "Successfully created challenge");
        } catch (ProjectException.UserNotExistException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (ProjectException.UserNotFoundException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(false, e.getMessage());
        }
    }

    public Boolean challengeFinished(ChallengeRequestDTO.createChallengeRequestDTO request) {
        // 현재 날짜를 LocalDate로 가져오기
        LocalDate currentDate = LocalDate.now();

        // 시작일과 종료일을 LocalDate로 변환
        LocalDate startDate = request.getChallenge_start_date().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate endDate = request.getChallenge_end_date().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // 현재 날짜가 시작일보다 이전인 경우 false 반환 (진행 중이지 않음)
        if (currentDate.isBefore(startDate)) {
            return false; // 챌린지가 시작되지 않음
        }

        // 현재 날짜가 종료일 이후인 경우 true 반환 (챌린지 종료)
        if (currentDate.isAfter(endDate)) {
            return true; // 챌린지가 종료됨
        }

        // 현재 날짜가 시작일과 종료일 사이에 있는 경우 false 반환 (진행 중인 챌린지)
        return false; // 챌린지가 진행 중
    }

    public ResponseDTO getChallengeByFinished(Boolean request) {
        try {
            List<Challenge> challengeList = challengeRepository.findChallengesByFinished(request);
            Collections.reverse(challengeList);
            List<ChallengeResponseDTO.FindChallengeResponse> responseList = challengeList.stream().map(ChallengeResponseDTO.FindChallengeResponse::new).toList();
            return new ResponseDTO(true, "Successfully fetched challenge information", responseList);
        } catch (Exception e) {
            return new ResponseDTO(false, e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void updateChallengesFinishedStatus() {
        LocalDate today = LocalDate.now();
        List<Challenge> challenges = challengeRepository.findAll();
        for (Challenge challenge : challenges) {
            Date endDate = challenge.getChallenge_end_date();
            LocalDate endLocalDate = convertToLocalDateViaInstant(endDate);
            if (endLocalDate.isBefore(today) || endLocalDate.isEqual(today)) {
                if (!challenge.getChallenge_finished()) {
                    challenge.setChallengeFinished(true);
                    challengeRepository.save(challenge);
                }
            }
        }
    }

    // Date를 LocalDate로 변환하는 메소드
    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public ResponseDTO findChallengeById(Long id, String token) {
        try {
            Optional<Challenge> challenge = challengeRepository.findById(id);
            if (!challenge.isPresent()) {
                return new ResponseDTO(false, "Challenge not found");
            }
            if (!userRepository.existsByEmail(jwtUtil.getEmail(token))) {
                throw new ProjectException.UserNotExistException("없는 유저입니다.");
            }
            User user = userRepository.findByEmail(jwtUtil.getEmail(token)).orElseThrow(() -> new ProjectException.UserNotFoundException("해당 유저는 존재하나, Repository에서 불러오는 과정에서 문제가 발생했습니다."));
            boolean joined = challenge.get().getUserChallengeRelationList().stream()
                    .anyMatch(userChallengeRelation -> userChallengeRelation.getUser().equals(user));

            return new ResponseDTO(true, "Successfully retrieved challenge", new ChallengeResponseDTO.FindChallengeBdIdResponse(challenge.get(), joined));
        } catch (ProjectException.UserNotFoundException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (ProjectException.UserNotExistException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(false, e.getMessage());
        }
    }

    @Transactional
    public ResponseDTO joinChallengeById(String token, Long challenge_id) {
        try {
            if (!userRepository.existsByEmail(jwtUtil.getEmail(token))) {
                throw new ProjectException.UserNotExistException("없는 유저입니다.");
            }
            User user = userRepository.findByEmail(jwtUtil.getEmail(token)).orElseThrow(() -> new ProjectException.UserNotFoundException("해당 유저는 존재하나, Repository에서 불러오는 과정에서 문제가 발생했습니다."));
            Optional<Challenge> challenge = challengeRepository.findById(challenge_id);
            if (!challenge.isPresent()) {
                throw new ProjectException.ChallengeNotExistException("없는 챌린지입니다.");
            }
            if (challenge.get().getUserChallengeRelationList().stream()
                    .anyMatch(userChallengeRelation -> userChallengeRelation.getUser().equals(user))) {
                return new ResponseDTO(true, "이미 참가한 챌린지입니다.");
            }
            UserChallengeRelation userChallengeRelation = new UserChallengeRelation(user, challenge.get());
            userChallengeRelationRepository.save(userChallengeRelation);
            return new ResponseDTO(true, "Successfully joined challenge");
        } catch (ProjectException.UserNotFoundException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (ProjectException.UserNotExistException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(false, "챌린지 가입 과정 중 처리되지 않은 예외가 발생했습니다. -> " + e.getMessage());
        }
    }

    @Transactional
    public ResponseDTO getDetailChallenge(String token) {
        try {
            if (!userRepository.existsByEmail(jwtUtil.getEmail(token))) {
                throw new ProjectException.UserNotExistException("없는 유저입니다.");
            }
            User user = userRepository.findByEmail(jwtUtil.getEmail(token)).orElseThrow(() -> new ProjectException.UserNotFoundException("해당 유저는 존재하나, Repository에서 불러오는 과정에서 문제가 발생했습니다."));
            List<ChallengeResponseDTO.FindAllChallengeByUserResponse> responseList = user.getUserChallengeRelationList()
                    .stream()
                    .map(userChallengeRelation -> new ChallengeResponseDTO.FindAllChallengeByUserResponse(userChallengeRelation.getChallenge()))
                    .toList();
            return new ResponseDTO(true, "Successfully retrieved challenge", responseList);
        } catch (ProjectException.UserNotFoundException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (ProjectException.UserNotExistException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(false, "챌린지 가입 과정 중 처리되지 않은 예외가 발생했습니다. -> " + e.getMessage());
        }
    }

    @Transactional
    public ResponseDTO updateChallengeLikeById(String token, Long challenge_id) {
        try {
            if (!userRepository.existsByEmail(jwtUtil.getEmail(token))) {
                throw new ProjectException.UserNotExistException("없는 유저입니다.");
            }
            User user = userRepository.findByEmail(jwtUtil.getEmail(token)).orElseThrow(() -> new ProjectException.UserNotFoundException("해당 유저는 존재하나, Repository에서 불러오는 과정에서 문제가 발생했습니다."));
            Optional<Challenge> challenge = challengeRepository.findById(challenge_id);
            if (!challenge.isPresent()) {
                throw new ProjectException.ChallengeNotExistException("없는 챌린지입니다.");
            }
            Optional<ChallengeLike> challengeLike = challengeLikeRepository.findByChallengeAndUser(challenge.get(), user);
            if (!challengeLike.isPresent()) {
                ChallengeLike challengeLike1 = new ChallengeLike(challenge.get(), user);
                challengeLikeRepository.save(challengeLike1);
                return new ResponseDTO(true, "Successfully updated challenge", true);
            } else {
                challengeLikeRepository.delete(challengeLike.get());
                return new ResponseDTO(true, "Successfully updated challenge", false);
            }
        } catch (ProjectException.ChallengeNotExistException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (ProjectException.UserNotFoundException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (ProjectException.UserNotExistException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(false, "챌린지 가입 과정 중 처리되지 않은 예외가 발생했습니다. -> " + e.getMessage());
        }
    }
}
