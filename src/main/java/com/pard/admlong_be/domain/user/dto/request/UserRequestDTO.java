package com.pard.admlong_be.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
