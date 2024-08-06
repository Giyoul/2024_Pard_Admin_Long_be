package com.pard.admlong_be.domain.bloodDonation.service;

import com.pard.admlong_be.domain.bloodDonation.dto.request.BloodDonationRequestDTO;
import com.pard.admlong_be.domain.bloodDonation.entity.BloodDonation;
import com.pard.admlong_be.domain.bloodDonation.repository.BloodDonationRepository;
import com.pard.admlong_be.domain.challengeDonationRelation.entity.ChallengeDonationRelation;
import com.pard.admlong_be.domain.challengeDonationRelation.repository.ChallengeDonationRelationRepository;
import com.pard.admlong_be.domain.user.entity.User;
import com.pard.admlong_be.domain.user.repository.UserRepository;
import com.pard.admlong_be.domain.userChallengeRelation.entity.UserChallengeRelation;
import com.pard.admlong_be.global.responses.error.exceptions.ProjectException;
import com.pard.admlong_be.global.security.jwt.JWTUtil;
import com.pard.admlong_be.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BloodDonationService {
    private final BloodDonationRepository bloodDonationRepository;
    private final UserRepository userRepository;
    private final ChallengeDonationRelationRepository challengeDonationRelationRepository;
    private final JWTUtil jwtUtil;

    @Transactional
    public ResponseDTO certifyBloodDonation(String token, BloodDonationRequestDTO.BloodDonationCertifyRequestDTO requestDTO) {
        try {
            if (!userRepository.existsByEmail(jwtUtil.getEmail(token))) {
                throw new ProjectException.UserNotExistException("없는 유저입니다.");
            }
            User user = userRepository.findByEmail(jwtUtil.getEmail(token)).orElseThrow(() -> new ProjectException.UserNotFoundException("해당 유저는 존재하나, Repository에서 불러오는 과정에서 문제가 발생했습니다."));
            BloodDonation bloodDonation = new BloodDonation(requestDTO.getDonation_date(), requestDTO.getDonation_location(), requestDTO.getDonation_type(), user);
            for (UserChallengeRelation userChallengeRelation : user.getUserChallengeRelationList()) {
                ChallengeDonationRelation challengeDonationRelation = new ChallengeDonationRelation(userChallengeRelation.getChallenge(), bloodDonation);
                challengeDonationRelationRepository.save(challengeDonationRelation);
            }
            user.addBloodDonation(bloodDonation);
            userRepository.save(user);
            bloodDonationRepository.save(bloodDonation);
            return new ResponseDTO(true, "bloodDonation successfully.");
        } catch (ProjectException.UserNotExistException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (ProjectException.UserNotFoundException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(false, e.getMessage());
        }
    }
}
