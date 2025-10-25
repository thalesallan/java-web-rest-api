package com.example.cleanapi.borders.validators;

import com.example.cleanapi.borders.dtos.CreateUserDto;
import com.example.cleanapi.borders.dtos.UpdateUserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Custom validator for user-related operations
 * Implements business validation rules beyond basic bean validation
 */
@Component
public class UserValidator {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    private static final Pattern NAME_PATTERN = Pattern.compile(
        "^[a-zA-ZÀ-ÿ\\s]+$"
    );
    
    /**
     * Validate CreateUserDto with business rules
     */
    public ValidationResult validateCreateUser(CreateUserDto dto) {
        List<String> errors = new ArrayList<>();
        
        if (dto == null) {
            errors.add("User data cannot be null");
            return new ValidationResult(false, errors);
        }
        
        // Validate name
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            errors.add("Name is required");
        } else {
            validateName(dto.getName(), errors);
        }
        
        // Validate email
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            errors.add("Email is required");
        } else {
            validateEmail(dto.getEmail(), errors);
        }
        
        return new ValidationResult(errors.isEmpty(), errors);
    }
    
    /**
     * Validate UpdateUserDto with business rules
     */
    public ValidationResult validateUpdateUser(UpdateUserDto dto) {
        List<String> errors = new ArrayList<>();
        
        if (dto == null) {
            errors.add("User data cannot be null");
            return new ValidationResult(false, errors);
        }
        
        // Validate name
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            errors.add("Name is required");
        } else {
            validateName(dto.getName(), errors);
        }
        
        // Validate email
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            errors.add("Email is required");
        } else {
            validateEmail(dto.getEmail(), errors);
        }
        
        return new ValidationResult(errors.isEmpty(), errors);
    }
    
    private void validateName(String name, List<String> errors) {
        String trimmedName = name.trim();
        
        if (trimmedName.length() < 2) {
            errors.add("Name must be at least 2 characters long");
        }
        
        if (trimmedName.length() > 100) {
            errors.add("Name must not exceed 100 characters");
        }
        
        if (!NAME_PATTERN.matcher(trimmedName).matches()) {
            errors.add("Name must contain only letters and spaces");
        }
        
        // Check for consecutive spaces
        if (trimmedName.contains("  ")) {
            errors.add("Name cannot contain consecutive spaces");
        }
    }
    
    private void validateEmail(String email, List<String> errors) {
        String trimmedEmail = email.trim().toLowerCase();
        
        if (trimmedEmail.length() > 254) {
            errors.add("Email must not exceed 254 characters");
        }
        
        if (!EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            errors.add("Email format is invalid");
        }
        
        // Check for common disposable email domains (business rule example)
        String[] disposableDomains = {"10minutemail.com", "tempmail.org", "guerrillamail.com"};
        for (String domain : disposableDomains) {
            if (trimmedEmail.endsWith("@" + domain)) {
                errors.add("Disposable email addresses are not allowed");
                break;
            }
        }
    }
    
    /**
     * Validation result wrapper
     */
    public static class ValidationResult {
        private final boolean valid;
        private final List<String> errors;
        
        public ValidationResult(boolean valid, List<String> errors) {
            this.valid = valid;
            this.errors = errors;
        }
        
        public boolean isValid() {
            return valid;
        }
        
        public List<String> getErrors() {
            return errors;
        }
        
        public String getErrorsAsString() {
            return String.join("; ", errors);
        }
    }
}
