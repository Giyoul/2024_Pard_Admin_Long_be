package com.pard.admlong_be.domain.challenge.service;

import com.pard.admlong_be.domain.challenge.dto.request.ChallengeRequestDTO;
import com.pard.admlong_be.domain.challenge.entity.Challenge;
import com.pard.admlong_be.domain.challenge.repository.ChallengeRepository;
import com.pard.admlong_be.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    @Transactional
    public ResponseDTO createChallenge(ChallengeRequestDTO.createChallengeRequestDTO request) {
        try {
            Challenge challenge = new Challenge();
            Boolean challenge_finished = challengeFinished(request);
            challenge.createChallenge(request, challenge_finished);
            challengeRepository.save(challenge);
            return  new ResponseDTO(true, "Successfully created challenge");
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
}
