package com.skillstack.skilltracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health Check", description = "Health check API")
public class HealthCheck {
    @GetMapping("/health")
    @Operation(summary = "Health check endpoint")  
    public String healthCheck() {
        return "OK";
    }
}
