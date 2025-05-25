package com.skillstack.skilltracker.mapper;

import com.skillstack.skilltracker.model.Skill;
import com.skillstack.skilltracker.model.User;
import com.skillstack.skilltracker.model.UserDTO;
import com.skillstack.skilltracker.repository.SkillRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final SkillRepository skillRepository;
    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        List<Long> skillIds = user.getSkills().stream()
                .map(Skill::getId)
                .collect(Collectors.toList());
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                skillIds,
                user.getRoles()
        );
    }

    public User toUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        List<Skill> skills = skillRepository.findAllById(userDTO.getSkillIds());
        return new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                skills,
                userDTO.getRoles());
    }
}
