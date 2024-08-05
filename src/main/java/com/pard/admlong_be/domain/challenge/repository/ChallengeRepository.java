package com.pard.admlong_be.domain.challenge.repository;

import com.pard.admlong_be.domain.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    @Query("SELECT c FROM Challenge c WHERE c.challenge_finished = :finished")
    List<Challenge> findChallengesByFinished(@Param("finished") boolean finished);


}
