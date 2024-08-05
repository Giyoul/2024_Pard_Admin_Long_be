package com.pard.admlong_be.domain.user.dto.response;

import com.pard.admlong_be.domain.challenge.dto.response.ChallengeResponseDTO;
import com.pard.admlong_be.domain.user.entity.User;
import com.pard.admlong_be.domain.user.service.UserService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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

    @Getter
    @Setter
    public static class GetUserResponseDTO {
        private String name;
        private Date birthday;
        private Integer gender;
        private Double height;
        private Double weight;
        private String blood_type;
        private ChallengeResponseDTO.GetChallengeResponse challenge_list;

        public GetUserResponseDTO(User user) {
            this.name = user.getName();
            this.birthday = user.getBirthday();
            this.gender = user.getGender();
            this.height = user.getHeight();
            this.weight = user.getWeight();
            this.blood_type = user.getBlood_type();
            this.challenge_list = null;
        }

        public GetUserResponseDTO(User user, ChallengeResponseDTO.GetChallengeResponse userChallenge) {
            this.name = user.getName();
            this.birthday = user.getBirthday();
            this.gender = user.getGender();
            this.height = user.getHeight();
            this.weight = user.getWeight();
            this.blood_type = user.getBlood_type();
            this.challenge_list = userChallenge;
        }
    }

    @Getter
    @Setter
    public static class GetDueDateResponseDTO {
        private long dueDate;

        public GetDueDateResponseDTO(long dueDate) {
            this.dueDate = dueDate;
        }
    }

    @Getter
    @Setter
    public static class GetShortUserInfoDTO {
        private String name;
        private String blood_type;

        public GetShortUserInfoDTO(User user) {
            this.name = user.getName();
            this.blood_type = user.getBlood_type();
        }
    }
}
