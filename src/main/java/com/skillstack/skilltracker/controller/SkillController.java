package com.skillstack.skilltracker.controller;


import com.skillstack.skilltracker.dto.SkillDTO;
import com.skillstack.skilltracker.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillDTO> createSkill(@RequestBody SkillDTO skillDTO){
        SkillDTO created = skillService.createSkill(skillDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getSkillById(@PathVariable long id){
        SkillDTO skillDTO = skillService.getSkillById(id);
        return ResponseEntity.ok(skillDTO);
    }

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAllSkills(){
        List<SkillDTO> skillDTOS = skillService.getAllSkills();
        return ResponseEntity.ok(skillDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable long id, @RequestBody SkillDTO skillDTO){
        SkillDTO updatedSkill = skillService.updateSkill(id, skillDTO);
        return ResponseEntity.ok(updatedSkill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable long id){
        boolean deleted = skillService.deleteSkill(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<SkillDTO>> findByCategory(@PathVariable String category){
        List<SkillDTO> skills = skillService.findByCategory(category);
        return ResponseEntity.ok(skills);
    }
}
