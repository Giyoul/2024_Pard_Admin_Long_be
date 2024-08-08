package com.pard.admlong_be.domain.challenge.dto.response;

import com.pard.admlong_be.domain.bloodDonation.dto.response.BloodDonationResponseDTO;
import com.pard.admlong_be.domain.challenge.entity.Challenge;
import com.pard.admlong_be.domain.user.dto.response.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ChallengeResponseDTO {

    @Getter
    @Setter
    public static class GetChallengeResponse {
        private Long challenge_id;
        private String challenge_name;
        private Date challenge_start_date;
        private Date challenge_end_date;
        private String challenge_age;
        private String challenge_org;
        private Integer challenge_like_count;

        public GetChallengeResponse(Challenge challenge) {
            this.challenge_id = challenge.getChallenge_id();
            this.challenge_name = challenge.getChallenge_name();
            this.challenge_start_date = challenge.getChallenge_start_date();
            this.challenge_end_date = challenge.getChallenge_end_date();
            this.challenge_age = challenge.getChallenge_age();
            this.challenge_org = challenge.getChallenge_org();
            this.challenge_like_count = challenge.getChallengeLikeList().size();
        }
    }

    @Getter
    @Setter
    public static class FindChallengeResponse {
        private Long challenge_id;
        private String challenge_name;
        private Date challenge_start_date;
        private Date challenge_end_date;
        private String challenge_age;
        private String challenge_description;
        private String challenge_org;
        private Integer challenge_gender;
        private Boolean challenge_finished;
        private Long user_count;

        public FindChallengeResponse(Challenge challenge) {
            this.challenge_id = challenge.getChallenge_id();
            this.challenge_name = challenge.getChallenge_name();
            this.challenge_start_date = challenge.getChallenge_start_date();
            this.challenge_end_date = challenge.getChallenge_end_date();
            this.challenge_age = challenge.getChallenge_age();
            this.challenge_description = challenge.getChallenge_description();
            this.challenge_org = challenge.getChallenge_org();
            this.challenge_gender = challenge.getChallenge_gender();
            this.challenge_finished = challenge.getChallenge_finished();
            this.user_count = (long) challenge.getUserChallengeRelationList().size();
        }
    }

    @Getter
    @Setter
    public static class FindChallengeBdIdResponse {
        private Long challenge_id;
        private Boolean challenge_user_joined;
        private String challenge_name;
        private Date challenge_start_date;
        private Date challenge_end_date;
        private String challenge_age;
        private String challenge_org;
        private Integer challenge_like_count;
        private String challenge_description;
        private Integer challenge_gender;
        List<UserResponseDTO.GetShortUserInfoDTO> user;

        public FindChallengeBdIdResponse(Challenge challenge, Boolean joined) {
            this.challenge_id = challenge.getChallenge_id();
            this.challenge_user_joined = joined;
            this.challenge_name = challenge.getChallenge_name();
            this.challenge_start_date = challenge.getChallenge_start_date();
            this.challenge_end_date = challenge.getChallenge_end_date();
            this.challenge_age = challenge.getChallenge_age();
            this.challenge_org = challenge.getChallenge_org();
            this.challenge_like_count = challenge.getChallengeLikeList().size();
            this.challenge_description = challenge.getChallenge_description();
            this.challenge_gender = challenge.getChallenge_gender();

            this.user = challenge.getUserChallengeRelationList().stream()
                    .map(userChallengeRelation -> new UserResponseDTO.GetShortUserInfoDTO(userChallengeRelation.getUser()))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    public static class FindAllChallengeByUserResponse {
        private Long challenge_id;
        private String challenge_name;
        private String challenge_description;
        private Date challenge_start_date;
        private Date challenge_end_date;
        private String challenge_age;
        private String challenge_org;
        private Integer challenge_like_count;
        private Integer challenge_gender;
        private Integer user_count;
        private List<BloodDonationResponseDTO.BloodDonationInfo> blood_donation;

        public FindAllChallengeByUserResponse(Challenge challenge) {
            this.challenge_id = challenge.getChallenge_id();
            this.challenge_name = challenge.getChallenge_name();
            this.challenge_description = challenge.getChallenge_description();
            this.challenge_start_date = challenge.getChallenge_start_date();
            this.challenge_end_date = challenge.getChallenge_end_date();
            this.challenge_age = challenge.getChallenge_age();
            this.challenge_org = challenge.getChallenge_org();
            this.challenge_like_count = challenge.getChallengeLikeList().size();
            this.challenge_gender = challenge.getChallenge_gender();
            this.user_count = (Integer) challenge.getUserChallengeRelationList().size();
            this.blood_donation = challenge.getChallengeDonationRelationList().stream()
                    .map(challengeDonationRelation -> new BloodDonationResponseDTO.BloodDonationInfo(challengeDonationRelation.getBloodDonation())).toList();
        }
    }
}
