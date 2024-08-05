package com.pard.admlong_be.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pard.admlong_be.domain.bloodDonation.entity.BloodDonation;
import com.pard.admlong_be.domain.challenge.entity.Challenge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    @JsonIgnore // 순환참조 방지
    private Challenge challenge;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
//    @JsonIgnore // 순환 참조 방지
//    private List<Challenge> challengeList;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
//    @JsonIgnore // 순환 참조 방지
//    private List<Challenge> likeChallengeList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // 순환 참조 방지
    private List<BloodDonation> bloodDonationList;
}
