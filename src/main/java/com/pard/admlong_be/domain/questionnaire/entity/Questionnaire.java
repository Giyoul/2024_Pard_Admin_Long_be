package com.pard.admlong_be.domain.questionnaire.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionnaire_id;

    private Long question1;
    private Long question2;
    private Long question3;
    private Long question4;
    private Long question5;
    private Long question6;
    private Long question7;
    private Long question8;
    private Long question9;
    private Long question10;
    private Long question11;

    private Date last_donation_date;

    private Integer donation_type;
}
