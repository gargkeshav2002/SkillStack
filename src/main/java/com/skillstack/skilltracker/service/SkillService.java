package com.skillstack.skilltracker.service;

import com.skillstack.skilltracker.SkilltrackerApplication;
import com.skillstack.skilltracker.dto.SkillDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SkillService {
     SkillDTO createSkill(String userName, SkillDTO skillDTO);
        SkillDTO getSkillById(long id);
        List<SkillDTO> getAllSkills();
        SkillDTO updateSkill(String userName, long id, SkillDTO skillDTO);
        boolean deleteSkill(String userName, long id);
        List<SkillDTO> findByCategory(String userName, String category);
      List<SkillDTO> getSkillsByUser(String userName);

}
