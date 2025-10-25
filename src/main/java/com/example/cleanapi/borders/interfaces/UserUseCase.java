package com.example.cleanapi.borders.interfaces;

import com.example.cleanapi.borders.dtos.CreateUserDto;
import com.example.cleanapi.borders.dtos.UpdateUserDto;
import com.example.cleanapi.borders.dtos.UserResponseDto;

import java.util.List;

/**
 * Use Case interface for User operations
 * Defines the contract for business logic operations
 */
public interface UserUseCase {
    
    /**
     * Create a new user
     * @param createUserDto Data for creating the user
     * @return The created user response
     * @throws IllegalArgumentException if email already exists
     */
    UserResponseDto createUser(CreateUserDto createUserDto);
    
    /**
     * Get a user by ID
     * @param id The user ID
     * @return The user response
     * @throws IllegalArgumentException if user not found
     */
    UserResponseDto getUserById(Long id);
    
    /**
     * Get all users
     * @return List of all users
     */
    List<UserResponseDto> getAllUsers();
    
    /**
     * Update an existing user
     * @param id The user ID
     * @param updateUserDto Data for updating the user
     * @return The updated user response
     * @throws IllegalArgumentException if user not found or email already exists
     */
    UserResponseDto updateUser(Long id, UpdateUserDto updateUserDto);
    
    /**
     * Delete a user by ID
     * @param id The user ID
     * @throws IllegalArgumentException if user not found
     */
    void deleteUser(Long id);
}
