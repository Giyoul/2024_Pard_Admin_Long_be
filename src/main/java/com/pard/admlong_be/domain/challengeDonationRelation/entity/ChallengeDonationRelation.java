package com.pard.admlong_be.domain.challengeDonationRelation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pard.admlong_be.domain.bloodDonation.entity.BloodDonation;
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
@Table(name = "challenge_donation_relation")
public class ChallengeDonationRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdRelation_id;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    @JsonIgnore // 순환참조 방지
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "blood_donation_id")
    @JsonIgnore // 순환참조 방지
    private BloodDonation bloodDonation;

    public ChallengeDonationRelation(Challenge challenge, BloodDonation bloodDonation) {
        this.challenge = challenge;
        this.bloodDonation = bloodDonation;
    }
}
