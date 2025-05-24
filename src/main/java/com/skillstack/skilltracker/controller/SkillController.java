package com.skillstack.skilltracker.controller;


import com.skillstack.skilltracker.dto.SkillDTO;
import com.skillstack.skilltracker.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
@Tag(name = "Skill", description = "Skill management API")
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    @Operation(summary = "Create a new skill")
    public ResponseEntity<SkillDTO> createSkill(@RequestBody SkillDTO skillDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        SkillDTO created = skillService.createSkill(userName, skillDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getSkillById(@PathVariable long id){
        SkillDTO skillDTO = skillService.getSkillById(id);
        return ResponseEntity.ok(skillDTO);
    }

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAllSkills(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<SkillDTO> skillDTOS = skillService.getAllSkills(   );
        return ResponseEntity.ok(skillDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable long id, @RequestBody SkillDTO skillDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        SkillDTO updatedSkill = skillService.updateSkill(userName, id, skillDTO);
        return ResponseEntity.ok(updatedSkill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean deleted = skillService.deleteSkill(userName, id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<SkillDTO>> findByCategory(@PathVariable String category){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<SkillDTO> skills = skillService.findByCategory(userName, category);
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SkillDTO>> getSkillsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<SkillDTO> skills = skillService.getSkillsByUser(userName);
        return ResponseEntity.ok(skills);
    }

}
