package com.skillstack.skilltracker.model;

import com.skillstack.skilltracker.dto.SkillDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String password;
    private List<Long> skillIds = new ArrayList<>(); // List of skills associated with the user
    private List<String> roles; // e.g., "USER", "ADMIN"
}
