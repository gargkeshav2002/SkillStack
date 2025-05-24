package com.skillstack.skilltracker.controller;

import com.skillstack.skilltracker.model.UserDTO;
import com.skillstack.skilltracker.service.UserService;
import com.skillstack.skilltracker.service.impl.UserDetailsServiceImpl;
import com.skillstack.skilltracker.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Public APIs", description = "Health check API")
@RequiredArgsConstructor
public class PublicControl {

    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/health")
    @Operation(summary = "Health check endpoint")  
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("api/v1/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PostMapping("api/v1/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(jwtToken);
        }catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
