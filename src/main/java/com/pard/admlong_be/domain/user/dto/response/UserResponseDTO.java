package com.pard.admlong_be.domain.user.dto.response;

import com.pard.admlong_be.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserResponseDTO {
    @Getter
    @Setter
    @Builder
    public static class UserInfo {
        private String name;
        private String email;

        public static UserInfo toDto(User user) {
            return UserInfo.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UserLoginResponseDTO {
        private boolean is_new_user;
        private String token;

        public UserLoginResponseDTO() {
            this.is_new_user = true;
            this.token = null;
        }

        public UserLoginResponseDTO(String token) {
            this.is_new_user = false;
            this.token = token;
        }
    }
}
