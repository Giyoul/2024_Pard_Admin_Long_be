package com.pard.admlong_be.domain.challenge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pard.admlong_be.domain.bloodDonation.entity.BloodDonation;
import com.pard.admlong_be.domain.challenge.dto.request.ChallengeRequestDTO;
import com.pard.admlong_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "challenge")
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challenge_id;

    private String challenge_name;
    private Date challenge_start_date;
    private Date challenge_end_date;
    private String challenge_age;
    private String challenge_description;
    private String challenge_org;
    private Integer challenge_like_count;
    private Boolean challenge_finished;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<User> userList;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore // 순환 참조 방지
    private List<BloodDonation> bloodDonationList;

    public void createChallenge(ChallengeRequestDTO.createChallengeRequestDTO request, Boolean challenge_finished) {
        this.challenge_name = request.getChallenge_name();
        this.challenge_start_date = request.getChallenge_start_date();
        this.challenge_end_date = request.getChallenge_end_date();
        this.challenge_description = request.getChallenge_description();
        this.challenge_org = request.getChallenge_org();
        this.challenge_age = request.getChallenge_age();
        this.challenge_like_count = 0;
        this.challenge_finished = challenge_finished;
    }

    public void setChallengeFinished(Boolean challenge_finished) {
        this.challenge_finished = challenge_finished;
    }

}
