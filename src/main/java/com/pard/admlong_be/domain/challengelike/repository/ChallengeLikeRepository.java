package com.pard.admlong_be.domain.challengelike.repository;

import com.pard.admlong_be.domain.challengelike.entity.ChallengeLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeLikeRepository extends JpaRepository<ChallengeLike, Long> {
}
