package com.pard.admlong_be.domain.questionnaire.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class QuestionnaireRequestDTO {
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
