package com.pard.admlong_be.domain.challengeDonationRelation.repository;

import com.pard.admlong_be.domain.challengeDonationRelation.entity.ChallengeDonationRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeDonationRelationRepository extends JpaRepository<ChallengeDonationRelation, Long> {
}
