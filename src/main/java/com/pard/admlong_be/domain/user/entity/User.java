package com.pard.admlong_be.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pard.admlong_be.domain.bloodDonation.entity.BloodDonation;
import com.pard.admlong_be.domain.challenge.entity.Challenge;
import com.pard.admlong_be.domain.challengelike.entity.ChallengeLike;
import com.pard.admlong_be.domain.userChallengeRelation.entity.UserChallengeRelation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String name;

    @Column(length = 100)
    private String email;

    private Date birthday;

    private Integer gender;

    private Double height;

    private Double weight;

    private String blood_type;

    private Date last_donation_date;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ChallengeLike> likeList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserChallengeRelation> userChallengeRelationList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // 순환 참조 방지
    private List<BloodDonation> bloodDonationList;
}
