package com.skillstack.skilltracker.repository;

import com.skillstack.skilltracker.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    // Custom query methods can be defined here if needed
    List<Skill> findByCategory(String category);
    List<Skill> findByUserId(Long userId);

    List<Skill> findByCategoryAndUserId(String category, long id);
}
