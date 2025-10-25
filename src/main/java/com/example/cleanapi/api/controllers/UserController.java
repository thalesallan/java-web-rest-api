package com.example.cleanapi.api.controllers;

import com.example.cleanapi.borders.dtos.CreateUserDto;
import com.example.cleanapi.borders.dtos.UpdateUserDto;
import com.example.cleanapi.borders.dtos.UserResponseDto;
import com.example.cleanapi.borders.interfaces.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for User operations
 * This is the entry point of the application following Clean Architecture
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management operations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    
    private final UserUseCase userUseCase;
    
    @Autowired
    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }
      @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "User with email already exists")
    })
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody CreateUserDto createUserDto) {
        
        UserResponseDto createdUser = userUseCase.createUser(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
      @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    public ResponseEntity<UserResponseDto> getUserById(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long id) {
        
        UserResponseDto user = userUseCase.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userUseCase.getAllUsers();
        return ResponseEntity.ok(users);
    }
      @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user with new information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "409", description = "Email already exists for another user")
    })
    public ResponseEntity<UserResponseDto> updateUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserDto updateUserDto) {
        
        UserResponseDto updatedUser = userUseCase.updateUser(id, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }
      @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by their unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long id) {
        
        userUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping("/health")
    // @Operation(summary = "Health check", description = "Simple health check endpoint")
    // @ApiResponse(responseCode = "200", description = "Service is healthy")
    // public ResponseEntity<String> healthCheck() {
    //     return ResponseEntity.ok("User service is running!");
    // }
}
