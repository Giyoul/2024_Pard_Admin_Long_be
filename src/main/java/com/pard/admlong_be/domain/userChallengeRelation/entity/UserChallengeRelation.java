package com.pard.admlong_be.domain.userChallengeRelation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pard.admlong_be.domain.challenge.entity.Challenge;
import com.pard.admlong_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_challenge_relation")
public class UserChallengeRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_challenge_relation_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // 순환참조 방지
    private User user;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    @JsonIgnore // 순환참조 방지
    private Challenge challenge;

    public UserChallengeRelation(User user, Challenge challenge) {
        this.user = user;
        this.challenge = challenge;
    }
}
