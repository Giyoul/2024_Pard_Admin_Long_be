package com.pard.admlong_be.domain.challengelike.entity;

import com.pard.admlong_be.domain.challenge.entity.Challenge;
import com.pard.admlong_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "challenge_like")
public class ChallengeLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challenge_like_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer like_count;
}
