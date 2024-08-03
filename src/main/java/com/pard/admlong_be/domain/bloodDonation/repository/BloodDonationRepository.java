package com.pard.admlong_be.domain.bloodDonation.repository;

import com.pard.admlong_be.domain.bloodDonation.entity.BloodDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodDonationRepository extends JpaRepository<BloodDonation, Long> {
}
