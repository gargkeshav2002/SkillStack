package com.skillstack.skilltracker.controller;

import com.skillstack.skilltracker.model.UserDTO;
import com.skillstack.skilltracker.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Creation API")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return ResponseEntity.ok(userService.getUserByUsername(userName));
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return ResponseEntity.ok(userService.updateUser(userName, userDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean deleted = userService.deleteUser(userName);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
