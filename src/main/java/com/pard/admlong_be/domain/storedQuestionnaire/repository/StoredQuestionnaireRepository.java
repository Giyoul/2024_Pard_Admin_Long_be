package com.pard.admlong_be.domain.storedQuestionnaire.repository;

import com.pard.admlong_be.domain.storedQuestionnaire.entity.StoredQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoredQuestionnaireRepository extends JpaRepository<StoredQuestionnaire, Long> {
    List<StoredQuestionnaire> findAllByQuestionNum(Long questionNum);
}
