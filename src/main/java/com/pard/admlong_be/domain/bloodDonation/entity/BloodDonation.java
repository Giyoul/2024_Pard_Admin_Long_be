package com.pard.admlong_be.domain.bloodDonation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pard.admlong_be.domain.challenge.entity.Challenge;
import com.pard.admlong_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "blood_donation")
public class BloodDonation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blood_donation_id;

    private Date donation_date;
    private String donation_location;
    private String donation_type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // 순환참조 방지
    private User user;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    @JsonIgnore // 순환참조 방지
    private Challenge challenge;
}
