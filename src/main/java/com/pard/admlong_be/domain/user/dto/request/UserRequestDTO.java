package com.pard.admlong_be.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

public class UserRequestDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Login {
        private String email;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Jwt {
        private String email;
        private String name;
    }

    @Getter
    @Setter
    public static class Register {
        private String email;
        private String name;
        private Date birthday;
        private Integer gender;
        private Double height;
        private Double weight;
        private String blood_type;
        private Date last_donation_date;
    }
}
