package com.skillstack.skilltracker.service.impl;

import com.skillstack.skilltracker.dto.SkillDTO;
import com.skillstack.skilltracker.exception.ResourceNotFoundException;
import com.skillstack.skilltracker.mapper.SkillMapper;
import com.skillstack.skilltracker.model.Skill;
import com.skillstack.skilltracker.model.User;
import com.skillstack.skilltracker.repository.SkillRepository;
import com.skillstack.skilltracker.repository.UserRepository;
import com.skillstack.skilltracker.service.SkillService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final UserRepository userRepository;

    // Implement the methods from SkillService interface
    @Override
    @Transactional
    public SkillDTO createSkill(String userName, SkillDTO skillDTO) {
        try{
            User user = userRepository.findByUsername(userName);
            if (user == null) {
                throw new ResourceNotFoundException("User", "username", userName);
            }

            Skill skill = skillMapper.toskill(skillDTO, user);
            Skill saved = skillRepository.save(skill);
            if (user.getSkills() == null) {
                user.setSkills(new ArrayList<>());
            }
            user.getSkills().add(saved);
            userRepository.save(user);
            return skillMapper.toskillDTO(saved);
        }catch (Exception e) {
            log.error("Error creating skill: {}", e.getMessage());
            throw new RuntimeException("Error creating skill: " + e.getMessage(), e);
        }
    }

    @Override
    public SkillDTO getSkillById(long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", id));
        return skillMapper.toskillDTO(skill);
    }

    @Override
    public List<SkillDTO> getAllSkills() {
        List<Skill> skills = skillRepository.findAll();
        List<SkillDTO> skillDTOS = new ArrayList<>();
        for (Skill skill : skills) {
            SkillDTO skillDTO = skillMapper.toskillDTO(skill);
            skillDTOS.add(skillDTO);
        }
        return skillDTOS;
    }

    @Override
    public SkillDTO updateSkill(String userName, long id, SkillDTO skillDTO) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new ResourceNotFoundException("User", "username", userName);
        }

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", id));

        if (!skill.getUser().getUsername().equals(userName)) {
            throw new SecurityException("You are not authorized to update this skill.");
        }

        skill.setName(skillDTO.getName());
        skill.setCategory(skillDTO.getCategory());
        skill.setLevel(skillDTO.getLevel());
        skill.setStartedOn(skillDTO.getStartedOn());
        skill.setUser(user); // Probably redundant, but safe

        Skill updatedSkill = skillRepository.save(skill);

        return skillMapper.toskillDTO(updatedSkill);
    }

    @Transactional
    @Override
    public boolean deleteSkill(String userName, long id) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new ResourceNotFoundException("User", "username", userName);
        }

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        if (!skill.getUser().getUsername().equals(userName)) {
            throw new SecurityException("You are not authorized to delete this skill.");
        }

        // ✅ Remove from the user's skill list
        user.getSkills().removeIf(s -> s.getId() == id);  // important line!

        userRepository.save(user); // persist the orphan removal

        return !skillRepository.existsById(id);
    }


    @Override
    public List<SkillDTO> findByCategory(String userName, String category) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new ResourceNotFoundException("User", "username", userName);
        }
        List<Skill> skills = skillRepository.findByCategoryAndUserId(category, user.getId());
        if (skills.isEmpty()) {
            throw new ResourceNotFoundException("Skill", "category", category);
        }
        return skills.stream()
                .map(skillMapper::toskillDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SkillDTO> getSkillsByUser(String userName) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new ResourceNotFoundException("User", "username", userName);
        }
        List<Skill> skills = skillRepository.findByUserId(user.getId());
        return skills.stream()
                .map(skillMapper::toskillDTO)
                .collect(Collectors.toList());
    }
}
