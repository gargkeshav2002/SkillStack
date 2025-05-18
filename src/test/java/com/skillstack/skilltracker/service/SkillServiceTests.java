package com.skillstack.skilltracker.service;

import com.skillstack.skilltracker.dto.SkillDTO;
import com.skillstack.skilltracker.model.Skill;
import com.skillstack.skilltracker.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SkillServiceTests {

    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillRepository skillRepository;

    @Test
    public void testGetSkillById(){
        SkillDTO skill = skillService.getSkillById(8L);
        assertNotNull(skill);
    }
}
