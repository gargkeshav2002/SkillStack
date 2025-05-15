package com.skillstack.skilltracker.service.impl;

import com.skillstack.skilltracker.dto.SkillDTO;
import com.skillstack.skilltracker.exception.ResourceNotFoundException;
import com.skillstack.skilltracker.mapper.SkillMapper;
import com.skillstack.skilltracker.model.Skill;
import com.skillstack.skilltracker.repository.SkillRepository;
import com.skillstack.skilltracker.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    // Implement the methods from SkillService interface
    @Override
    public SkillDTO createSkill(SkillDTO skillDTO) {
        Skill skill = SkillMapper.toskill(skillDTO);
        Skill saved = skillRepository.save(skill);
        return SkillMapper.toskillDTO(saved);
    }

    @Override
    public SkillDTO getSkillById(long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", id));
        return SkillMapper.toskillDTO(skill);
    }

    @Override
    public List<SkillDTO> getAllSkills() {
        List<Skill> skills = skillRepository.findAll();
        List<SkillDTO> skillDTOS = new ArrayList<>();
        for (Skill skill : skills) {
            SkillDTO skillDTO = SkillMapper.toskillDTO(skill);
            skillDTOS.add(skillDTO);
        }
        return skillDTOS;
    }

    @Override
    public SkillDTO updateSkill(long id, SkillDTO skillDTO) {
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        existingSkill.setName(skillDTO.getName());
        existingSkill.setCategory(skillDTO.getCategory());
        existingSkill.setLevel(skillDTO.getLevel());
        existingSkill.setStartedOn(skillDTO.getStartedOn());
        Skill updatedSkill = skillRepository.save(existingSkill);
        return SkillMapper.toskillDTO(updatedSkill);
    }

    @Override
    public boolean deleteSkill(long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        skillRepository.delete(skill);
        return !skillRepository.existsById(id);
    }

    @Override
    public List<SkillDTO> findByCategory(String category) {
        List<Skill> skills = skillRepository.findByCategory(category);
        return skills.stream()
                .map(SkillMapper::toskillDTO)
                .collect(Collectors.toList());
    }
}
