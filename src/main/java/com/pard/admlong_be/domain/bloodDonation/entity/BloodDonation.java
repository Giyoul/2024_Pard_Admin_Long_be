package com.pard.admlong_be.domain.bloodDonation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pard.admlong_be.domain.challenge.entity.Challenge;
import com.pard.admlong_be.domain.challengeDonationRelation.entity.ChallengeDonationRelation;
import com.pard.admlong_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
    private Integer donation_type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // 순환참조 방지
    private User user;

    @OneToMany(mappedBy = "bloodDonation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // 순환 참조 방지
    private List<ChallengeDonationRelation> challengeDonationRelationList;

    public BloodDonation(Date donation_date, String donation_location, Integer donation_type, User user) {
        this.donation_date = donation_date;
        this.donation_location = donation_location;
        this.donation_type = donation_type;
        this.user = user;
    }

    public void addRelation(ChallengeDonationRelation challengeDonationRelation) {
        this.challengeDonationRelationList.add(challengeDonationRelation);
    }
}
