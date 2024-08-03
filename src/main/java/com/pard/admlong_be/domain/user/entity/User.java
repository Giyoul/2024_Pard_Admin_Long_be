package com.pard.admlong_be.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    private String name;

    @Column(length = 100)
    private String email;

    private Date birthday;

    private Integer gender;

    private Double height;

    private Double weight;

    private String blood_type;

    private Date last_donation_date;
}
