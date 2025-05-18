package com.skillstack.skilltracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstack.skilltracker.dto.SkillDTO;
import com.skillstack.skilltracker.service.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SkillController.class)
public class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillService skillService;

    @Autowired
    private ObjectMapper objectMapper;

    private SkillDTO skillDTO;

    @BeforeEach
    void setUp() {
        skillDTO = new SkillDTO(1L, "Java", "Backend", "Intermediate", new Date());
    }

    @Test
    void testCreateSkill() throws Exception {
        when(skillService.createSkill(any(SkillDTO.class))).thenReturn(skillDTO);

        mockMvc.perform(post("/api/v1/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void testGetSkillById() throws Exception {
        when(skillService.getSkillById(1L)).thenReturn(skillDTO);

        mockMvc.perform(get("/api/v1/skills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void testGetAllSkills() throws Exception {
        List<SkillDTO> skills = Arrays.asList(skillDTO);
        when(skillService.getAllSkills()).thenReturn(skills);

        mockMvc.perform(get("/api/v1/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testUpdateSkill() throws Exception {
        when(skillService.updateSkill(Mockito.eq(1L), any(SkillDTO.class))).thenReturn(skillDTO);

        mockMvc.perform(put("/api/v1/skills/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void testDeleteSkill() throws Exception {
        when(skillService.deleteSkill(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/skills/1"))
                .andExpect(status().isNoContent());

        when(skillService.deleteSkill(2L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/skills/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetByCategory() throws Exception {
        List<SkillDTO> skills = Arrays.asList(skillDTO);
        when(skillService.findByCategory("Backend")).thenReturn(skills);

        mockMvc.perform(get("/api/v1/skills/category/Backend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
