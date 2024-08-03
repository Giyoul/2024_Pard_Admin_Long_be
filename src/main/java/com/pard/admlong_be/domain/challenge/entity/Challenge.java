package com.pard.admlong_be.domain.challenge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pard.admlong_be.domain.bloodDonation.entity.BloodDonation;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // 순환참조 방지
    private User user;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore // 순환 참조 방지
    private List<BloodDonation> bloodDonationList;

}
