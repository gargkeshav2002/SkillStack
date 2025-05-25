package com.skillstack.skilltracker.mapper;

import com.skillstack.skilltracker.dto.SkillDTO;
import com.skillstack.skilltracker.model.Skill;
import com.skillstack.skilltracker.model.User;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {
    public SkillDTO toskillDTO(Skill skill) {
        if(skill == null) {
            return null;
        }
        return new SkillDTO(
                skill.getId(),
                skill.getName(),
                skill.getCategory(),
                skill.getLevel(),
                skill.getStartedOn(),
                skill.getUser() != null ? skill.getUser().getId() : null);
    }

    public Skill toskill(SkillDTO skillDTO, User user) {
        if(skillDTO == null) {
            return null;
        }
        return new Skill(
                skillDTO.getId(),
                skillDTO.getName(),
                skillDTO.getCategory(),
                skillDTO.getLevel(),
                skillDTO.getStartedOn(),
                user);
    }
}
