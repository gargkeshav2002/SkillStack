package com.skillstack.skilltracker.mapper;

import com.skillstack.skilltracker.dto.SkillDTO;
import com.skillstack.skilltracker.model.Skill;

public class SkillMapper {
    public static SkillDTO toskillDTO(Skill skill) {
        if(skill == null) {
            return null;
        }
        return new SkillDTO(
                skill.getId(),
                skill.getName(),
                skill.getCategory(),
                skill.getLevel(),
                skill.getStartedOn());
    }

    public static Skill toskill(SkillDTO skillDTO) {
        if(skillDTO == null) {
            return null;
        }
        return new Skill(
                skillDTO.getId(),
                skillDTO.getName(),
                skillDTO.getCategory(),
                skillDTO.getLevel(),
                skillDTO.getStartedOn());
    }
}
