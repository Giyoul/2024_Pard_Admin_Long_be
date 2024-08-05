package com.pard.admlong_be.domain.userChallengeRelation.repository;

import com.pard.admlong_be.domain.userChallengeRelation.entity.UserChallengeRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChallengeRelationRepository extends JpaRepository<UserChallengeRelation, Long> {
}
