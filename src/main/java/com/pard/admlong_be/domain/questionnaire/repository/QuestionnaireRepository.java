package com.pard.admlong_be.domain.questionnaire.repository;

import com.pard.admlong_be.domain.questionnaire.entity.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
}
