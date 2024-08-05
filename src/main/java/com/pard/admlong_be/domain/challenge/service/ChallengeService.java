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
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            ChallengeLike challengeLike = new ChallengeLike(challenge);
            Boolean challenge_finished = challengeFinished(request);
            challenge.createChallenge(request, challenge_finished);
            if (!userRepository.existsByEmail(jwtUtil.getEmail(token))) {
                throw new ProjectException.UserNotExistException("없는 유저입니다.");
            }
            User user = userRepository.findByEmail(jwtUtil.getEmail(token)).orElseThrow(() -> new ProjectException.UserNotFoundException("해당 유저는 존재하나, Repository에서 불러오는 과정에서 문제가 발생했습니다."));
            UserChallengeRelation userChallengeRelation = new UserChallengeRelation(user, challenge);
            userChallengeRelationRepository.save(userChallengeRelation);
            challengeRepository.save(challenge);
            challengeLikeRepository.save(challengeLike);
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

        // 현재 날짜가 시작일과 종료일 사이에 있는지 확인
        boolean isBetween = !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);

        return !isBetween;
    }

    public ResponseDTO getChallengeByFinished(Boolean request) {
        try {
            List<Challenge> challengeList = challengeRepository.findChallengesByFinished(request);
            return new ResponseDTO(true, "Successfully fetched challenge information", challengeList);
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

    public ResponseDTO findChallengeById(Long id) {
        try {
            Optional<Challenge> challenge = challengeRepository.findById(id);
            if (!challenge.isPresent()) {
                return new ResponseDTO(false, "Challenge not found");
            }
            return new ResponseDTO(true, "Successfully retrieved challenge", new ChallengeResponseDTO.FindChallengeBdIdResponse(challenge.get()));
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
}
