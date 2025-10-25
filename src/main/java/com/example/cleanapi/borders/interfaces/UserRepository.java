package com.example.cleanapi.borders.interfaces;

import com.example.cleanapi.borders.entities.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface following Clean Architecture principles
 * This interface belongs to the domain layer and defines the contract
 * for data persistence without depending on implementation details
 */
public interface UserRepository {
    
    /**
     * Save a new user or update an existing one
     * @param user The user to save
     * @return The saved user with generated ID
     */
    User save(User user);
    
    /**
     * Find a user by their ID
     * @param id The user ID
     * @return Optional containing the user if found
     */
    Optional<User> findById(Long id);
    
    /**
     * Find a user by their email address
     * @param email The user email
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find all users
     * @return List of all users
     */
    List<User> findAll();
    
    /**
     * Delete a user by their ID
     * @param id The user ID
     * @return true if user was deleted, false if not found
     */
    boolean deleteById(Long id);
    
    /**
     * Check if a user exists by ID
     * @param id The user ID
     * @return true if user exists
     */
    boolean existsById(Long id);
    
    /**
     * Check if a user exists by email
     * @param email The user email
     * @return true if user exists
     */
    boolean existsByEmail(String email);
}
