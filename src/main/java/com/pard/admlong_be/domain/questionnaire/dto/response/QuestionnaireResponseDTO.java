package com.pard.admlong_be.domain.questionnaire.dto.response;

import com.pard.admlong_be.domain.questionnaire.dto.request.QuestionnaireRequestDTO;
import com.pard.admlong_be.domain.storedQuestionnaire.repository.StoredQuestionnaireRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class QuestionnaireResponseDTO {


    @Getter
    @Setter
    public static class response{
        private Boolean eligibility;
        private Date last_donation_date;
        private reason reason;

        public response(QuestionnaireRequestDTO request) {
            this.eligibility = true;
            this.last_donation_date = request.getLast_donation_date();
            this.reason = null;
        }

        public response(QuestionnaireRequestDTO request, reason reason) {
            this.eligibility = false;
            this.last_donation_date = request.getLast_donation_date();
            this.reason = reason;
        }
    }

    @Getter
    @Setter
    public static class reason {
        private String blood_type_reason;
        private Integer due_date;
        private String reason1;
        private String reason2;
        private String reason3;
        private String reason4;
        private String reason5;
        private String reason6;
        private String reason7;
        private String reason8;
        private String reason9;
        private String reason10;
        private String reason11;

        public reason(String blood_type_reason, Integer due_date, String reason1, String reason2, String reason3, String reason4, String reason5, String reason6, String reason7, String reason8, String reason9, String reason10, String reason11) {
            this.reason1 = reason1;
            this.reason2 = reason2;
            this.reason3 = reason3;
            this.reason4 = reason4;
            this.reason5 = reason5;
            this.reason6 = reason6;
            this.reason7 = reason7;
            this.reason8 = reason8;
            this.reason9 = reason9;
            this.reason10 = reason10;
            this.reason11 = reason11;
            this.blood_type_reason = blood_type_reason;
            this.due_date = due_date;
        }
    }
}
