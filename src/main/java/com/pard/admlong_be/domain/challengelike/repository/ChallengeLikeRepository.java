package com.pard.admlong_be.domain.challengelike.repository;

import com.pard.admlong_be.domain.challenge.entity.Challenge;
import com.pard.admlong_be.domain.challengelike.entity.ChallengeLike;
import com.pard.admlong_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeLikeRepository extends JpaRepository<ChallengeLike, Long> {
    Optional<ChallengeLike> findByChallengeAndUser(Challenge challenge, User user);
}
