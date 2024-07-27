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
}
