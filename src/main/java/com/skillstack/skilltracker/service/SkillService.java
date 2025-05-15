package com.skillstack.skilltracker.service;

import com.skillstack.skilltracker.SkilltrackerApplication;
import com.skillstack.skilltracker.dto.SkillDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SkillService {
     SkillDTO createSkill(SkillDTO skillDTO);
        SkillDTO getSkillById(long id);
        List<SkillDTO> getAllSkills();
        SkillDTO updateSkill(long id, SkillDTO skillDTO);
        boolean deleteSkill(long id);
        List<SkillDTO> findByCategory(String category);
}
