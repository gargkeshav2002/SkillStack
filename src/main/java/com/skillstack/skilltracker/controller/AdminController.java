package com.skillstack.skilltracker.controller;

import com.skillstack.skilltracker.model.UserDTO;
import com.skillstack.skilltracker.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin APIs")
public class AdminController {

    private final UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);

    }

    @PostMapping
    public ResponseEntity<UserDTO> createAdminUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createAdminUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }
}
