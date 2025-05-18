package com.skillstack.skilltracker.service;


import com.skillstack.skilltracker.dto.SkillDTO;
import com.skillstack.skilltracker.exception.ResourceNotFoundException;
import com.skillstack.skilltracker.mapper.SkillMapper;
import com.skillstack.skilltracker.model.Skill;
import com.skillstack.skilltracker.repository.SkillRepository;
import com.skillstack.skilltracker.service.impl.SkillServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillServiceImplTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillServiceImpl skillServiceImpl;

    @Test
    public void testCreateSkill(){
        SkillDTO skillDTO = new SkillDTO(1L, "Java", "Programming", "Advanced", Date.valueOf("2023-01-01"));
        Skill skill = SkillMapper.toskill(skillDTO);

        when(skillRepository.save(any(Skill.class))).thenReturn(skill);

        SkillDTO savedSkill = skillServiceImpl.createSkill(skillDTO);

        Assertions.assertNotNull(savedSkill);
        Assertions.assertEquals(skillDTO.getName(), savedSkill.getName());

        verify(skillRepository, times(1)).save(any(Skill.class));
    }

    @Test
    public void testGetSkillById(){
        // Arrange
        long skillId = 1L;
        SkillDTO skillDTO = new SkillDTO(skillId, "Java", "Programming", "Advanced", Date.valueOf("2023-01-01"));
        Skill skill = SkillMapper.toskill(skillDTO);

        when(skillRepository.findById(skillId)).thenReturn(java.util.Optional.of(skill));

        // Act
        SkillDTO result = skillServiceImpl.getSkillById(skillId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(skillDTO.getName(), result.getName());
        Assertions.assertEquals(skillDTO.getCategory(), result.getCategory());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            skillServiceImpl.getSkillById(2L);
        });

        verify(skillRepository, times(1)).findById(skillId);
    }

    @Test
    public void testGetAllSkills(){
        SkillDTO skillDTO = new SkillDTO(1L, "Java", "Programming", "Advanced", Date.valueOf("2023-01-01"));
        Skill skill = SkillMapper.toskill(skillDTO);

        when(skillRepository.findAll()).thenReturn(List.of(skill));

        List<SkillDTO> result = skillServiceImpl.getAllSkills();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Java", result.get(0).getName());

        verify(skillRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateSkill(){
        long skillId = 1L;;
        SkillDTO skillDTO = new SkillDTO(skillId, "Java", "Programming", "Advanced", Date.valueOf("2023-01-01"));
        Skill skill = SkillMapper.toskill(skillDTO);

        when(skillRepository.findById(skillId)).thenReturn(java.util.Optional.of(skill));
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);

        SkillDTO updatedSkill = skillServiceImpl.updateSkill(skillId, skillDTO);

        Assertions.assertNotNull(updatedSkill);
        Assertions.assertEquals(skillDTO.getName(), updatedSkill.getName());
        Assertions.assertEquals(skillDTO.getCategory(), updatedSkill.getCategory());
        Assertions.assertThrows(RuntimeException.class, () -> {
            skillServiceImpl.getSkillById(2L);
        });

        verify(skillRepository, times(1)).findById(skillId);
        verify(skillRepository, times(1)).save(any(Skill.class));
    }

    @Test
    public void testDeleteSkill(){
        long skillId = 1L;
        SkillDTO skillDTO = new SkillDTO(skillId, "Java", "Programming", "Advanced", Date.valueOf("2023-01-01"));
        Skill skill = SkillMapper.toskill(skillDTO);

        when(skillRepository.findById(skillId)).thenReturn(java.util.Optional.of(skill));
        doNothing().when(skillRepository).delete(any(Skill.class));

        boolean deleted = skillServiceImpl.deleteSkill(skillId);

        Assertions.assertTrue(deleted);
        Assertions.assertThrows(RuntimeException.class, () -> {
            skillServiceImpl.getSkillById(2L);
        });

        verify(skillRepository, times(1)).findById(skillId);
        verify(skillRepository, times(1)).delete(any(Skill.class));
    }

    @Test
    public void testFindByCategory(){
        String category = "Programming";
        SkillDTO skillDTO = new SkillDTO(1L, "Java", category, "Advanced", Date.valueOf("2023-01-01"));
        Skill skill = SkillMapper.toskill(skillDTO);

        when(skillRepository.findByCategory(category)).thenReturn(List.of(skill));

        List<SkillDTO> result = skillServiceImpl.findByCategory(category);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(category, result.get(0).getCategory());

        verify(skillRepository, times(1)).findByCategory(category);
    }
}
