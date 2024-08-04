package com.pard.admlong_be.domain.challenge.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class ChallengeRequestDTO {

    @Getter
    @Setter
    public static class createChallengeRequestDTO {
        private String challenge_name;
        private String challenge_description;
        private Date challenge_start_date;
        private Date challenge_end_date;
        private String challenge_age;
        private String challenge_org;
    }
}
