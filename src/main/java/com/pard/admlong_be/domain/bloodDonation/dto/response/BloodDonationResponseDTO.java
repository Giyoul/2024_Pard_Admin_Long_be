package com.pard.admlong_be.domain.bloodDonation.dto.response;

import com.pard.admlong_be.domain.bloodDonation.entity.BloodDonation;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class BloodDonationResponseDTO {
    @Getter
    @Setter
    public static class BloodDonationInfo {
        private String donation_user_name;
        private Date donation_date;
        private String donation_location;
        private Integer donation_type;

        public BloodDonationInfo(BloodDonation bloodDonation) {
            this.donation_user_name = bloodDonation.getUser().getName();
            this.donation_date = bloodDonation.getDonation_date();
            this.donation_location = bloodDonation.getDonation_location();
            this.donation_type = bloodDonation.getDonation_type();
        }
    }
}
