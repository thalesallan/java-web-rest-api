package com.example.cleanapi.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Root controller for basic API information
 */
@RestController
@RequestMapping("/")
@Tag(name = "Root", description = "Basic API information")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RootController {

    @GetMapping
    @Operation(summary = "API Information", description = "Returns basic information about the API")
    @ApiResponse(responseCode = "200", description = "API information retrieved successfully")
    public ResponseEntity<Map<String, Object>> getApiInfo() {
        Map<String, Object> info = Map.of(
            "name", "Java Web REST API - Clean Architecture",
            "version", "1.0.0",
            "description", "A REST API built with Clean Architecture principles",
            "author", "Thales Allan Dev",
            "endpoints", Map.of(
                "users", "/api/v1/users",
                "health", "/api/v1/users/health",
                "swagger", "/swagger-ui.html",
                "docs", "/api-docs"
            ),
            "status", "running"
        );
        
        return ResponseEntity.ok(info);
    }

    @GetMapping("/status")
    @Operation(summary = "Service Status", description = "Quick status check")
    @ApiResponse(responseCode = "200", description = "Service is running")
    public ResponseEntity<Map<String, String>> getStatus() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "Java Web REST API",
            "timestamp", java.time.Instant.now().toString()
        ));
    }
}
