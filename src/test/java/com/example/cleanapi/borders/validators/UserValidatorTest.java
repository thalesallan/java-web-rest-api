package com.example.cleanapi.borders.validators;

import com.example.cleanapi.borders.dtos.CreateUserDto;
import com.example.cleanapi.borders.dtos.UpdateUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UserValidator
 */
@DisplayName("User Validator Tests")
class UserValidatorTest {

    private UserValidator userValidator;

    @BeforeEach
    void setUp() {
        userValidator = new UserValidator();
    }

    @Test
    @DisplayName("Should validate valid CreateUserDto successfully")
    void shouldValidateValidCreateUserDto() {
        // Arrange
        CreateUserDto dto = new CreateUserDto("John Doe", "john.doe@example.com");

        // Act
        UserValidator.ValidationResult result = userValidator.validateCreateUser(dto);

        // Assert
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    @DisplayName("Should fail validation when CreateUserDto is null")
    void shouldFailValidationWhenCreateUserDtoIsNull() {
        // Act
        UserValidator.ValidationResult result = userValidator.validateCreateUser(null);

        // Assert
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals("User data cannot be null", result.getErrors().get(0));
    }

    @Test
    @DisplayName("Should fail validation when name is too short")
    void shouldFailValidationWhenNameIsTooShort() {
        // Arrange
        CreateUserDto dto = new CreateUserDto("J", "john.doe@example.com");

        // Act
        UserValidator.ValidationResult result = userValidator.validateCreateUser(dto);

        // Assert
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Name must be at least 2 characters long"));
    }

    @Test
    @DisplayName("Should fail validation when name is too long")
    void shouldFailValidationWhenNameIsTooLong() {
        // Arrange
        String longName = "A".repeat(101);
        CreateUserDto dto = new CreateUserDto(longName, "john.doe@example.com");

        // Act
        UserValidator.ValidationResult result = userValidator.validateCreateUser(dto);

        // Assert
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Name must not exceed 100 characters"));
    }

    @Test
    @DisplayName("Should fail validation when name contains numbers")
    void shouldFailValidationWhenNameContainsNumbers() {
        // Arrange
        CreateUserDto dto = new CreateUserDto("John123", "john.doe@example.com");

        // Act
        UserValidator.ValidationResult result = userValidator.validateCreateUser(dto);

        // Assert
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Name must contain only letters and spaces"));
    }

    @Test
    @DisplayName("Should fail validation when name has consecutive spaces")
    void shouldFailValidationWhenNameHasConsecutiveSpaces() {
        // Arrange
        CreateUserDto dto = new CreateUserDto("John  Doe", "john.doe@example.com");

        // Act
        UserValidator.ValidationResult result = userValidator.validateCreateUser(dto);

        // Assert
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Name cannot contain consecutive spaces"));
    }

    @Test
    @DisplayName("Should fail validation when email format is invalid")
    void shouldFailValidationWhenEmailFormatIsInvalid() {
        // Arrange
        CreateUserDto dto = new CreateUserDto("John Doe", "invalid-email");

        // Act
        UserValidator.ValidationResult result = userValidator.validateCreateUser(dto);

        // Assert
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Email format is invalid"));
    }

    @Test
    @DisplayName("Should fail validation when email is disposable")
    void shouldFailValidationWhenEmailIsDisposable() {
        // Arrange
        CreateUserDto dto = new CreateUserDto("John Doe", "test@10minutemail.com");

        // Act
        UserValidator.ValidationResult result = userValidator.validateCreateUser(dto);

        // Assert
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Disposable email addresses are not allowed"));
    }

    @Test
    @DisplayName("Should validate valid UpdateUserDto successfully")
    void shouldValidateValidUpdateUserDto() {
        // Arrange
        UpdateUserDto dto = new UpdateUserDto("Jane Doe", "jane.doe@example.com");

        // Act
        UserValidator.ValidationResult result = userValidator.validateUpdateUser(dto);

        // Assert
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    @DisplayName("Should fail validation when email is too long")
    void shouldFailValidationWhenEmailIsTooLong() {
        // Arrange
        String longEmail = "a".repeat(250) + "@example.com";
        CreateUserDto dto = new CreateUserDto("John Doe", longEmail);

        // Act
        UserValidator.ValidationResult result = userValidator.validateCreateUser(dto);

        // Assert
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Email must not exceed 254 characters"));
    }
}
