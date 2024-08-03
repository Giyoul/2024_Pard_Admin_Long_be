package com.pard.admlong_be.domain.challenge.dto.response;

import com.pard.admlong_be.domain.challenge.entity.Challenge;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
            this.challenge_like_count = challenge.getChallenge_like_count();
        }
    }
}
