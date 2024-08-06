package com.pard.admlong_be.domain.bloodDonation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class BloodDonationRequestDTO {

    @Getter
    @Setter
    public static class BloodDonationCertifyRequestDTO {
        private Date donation_date;
        private String donation_location;
        private String donation_type;
    }
}
