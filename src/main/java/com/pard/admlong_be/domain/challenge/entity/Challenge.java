package com.pard.admlong_be.domain.challenge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHALLENGE_ID")
    private Long id;

    private String challenge_name;
    private Date challenge_start_date;
    private Date challenge_end_date;
    private String challenge_age;
    private String challenge_description;
    private String challenge_org;
    private Integer challenge_like_count;
    private Boolean challenge_finished;
}
