package com.example.cleanapi.usecases;

import com.example.cleanapi.borders.dtos.CreateUserDto;
import com.example.cleanapi.borders.dtos.UpdateUserDto;
import com.example.cleanapi.borders.dtos.UserResponseDto;
import com.example.cleanapi.borders.entities.User;
import com.example.cleanapi.borders.interfaces.UserRepository;
import com.example.cleanapi.borders.interfaces.UserUseCase;
import com.example.cleanapi.borders.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of UserUseCase containing business logic
 * This class orchestrates business operations and enforces business rules
 */
@Service
public class UserUseCaseImpl implements UserUseCase {
    
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    
    @Autowired
    public UserUseCaseImpl(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }
    
    @Override
    public UserResponseDto createUser(CreateUserDto createUserDto) {
        // Validate input
        UserValidator.ValidationResult validationResult = userValidator.validateCreateUser(createUserDto);
        if (!validationResult.isValid()) {
            throw new IllegalArgumentException("Validation failed: " + validationResult.getErrorsAsString());
        }
        
        // Business rule: Check if email already exists
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        
        // Create and save user
        User user = new User(createUserDto.getName(), createUserDto.getEmail());
        User savedUser = userRepository.save(user);
        
        // Convert to response DTO
        return mapToResponseDto(savedUser);
    }
    
    @Override
    public UserResponseDto getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
        
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        
        return mapToResponseDto(user);
    }
    
    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                   .map(this::mapToResponseDto)
                   .collect(Collectors.toList());
    }
    
    @Override
    public UserResponseDto updateUser(Long id, UpdateUserDto updateUserDto) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
        
        // Validate input
        UserValidator.ValidationResult validationResult = userValidator.validateUpdateUser(updateUserDto);
        if (!validationResult.isValid()) {
            throw new IllegalArgumentException("Validation failed: " + validationResult.getErrorsAsString());
        }
        
        // Find existing user
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        
        // Business rule: Check if email is being changed to an existing email
        if (!existingUser.getEmail().equals(updateUserDto.getEmail()) && 
            userRepository.existsByEmail(updateUserDto.getEmail())) {
            throw new IllegalArgumentException("Another user with this email already exists");
        }
        
        // Update user using entity business method
        existingUser.updateUser(updateUserDto.getName(), updateUserDto.getEmail());
        
        // Save and return
        User updatedUser = userRepository.save(existingUser);
        return mapToResponseDto(updatedUser);
    }
    
    @Override
    public void deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
        
        // Business rule: Verify user exists before deletion
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        
        boolean deleted = userRepository.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Failed to delete user with ID: " + id);
        }
    }
    
    /**
     * Private helper method to convert User entity to UserResponseDto
     */
    private UserResponseDto mapToResponseDto(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}
