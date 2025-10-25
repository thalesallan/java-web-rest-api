package com.example.cleanapi.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Health check", description = "Simple health check")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Simple health check endpoint")
    @ApiResponse(responseCode = "200", description = "Service is healthy")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("User service is running!");
    }

    @RequestMapping(value = "/health", method = RequestMethod.OPTIONS)
    @Operation(summary = "Health check OPTIONS", description = "Handle preflight requests for health endpoint")
    @ApiResponse(responseCode = "200", description = "Preflight successful")
    public ResponseEntity<Void> healthCheckOptions() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cors-test")
    @Operation(summary = "CORS Test", description = "Endpoint para testar configurações CORS")
    @ApiResponse(responseCode = "200", description = "CORS test successful")
    public ResponseEntity<Map<String, Object>> corsTest(@RequestBody(required = false) Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "CORS test successful!");
        response.put("timestamp", System.currentTimeMillis());
        response.put("receivedData", body);
        return ResponseEntity.ok(response);
    }
}
