package com.example.cleanapi.usecases;

import com.example.cleanapi.borders.dtos.CreateUserDto;
import com.example.cleanapi.borders.dtos.UpdateUserDto;
import com.example.cleanapi.borders.dtos.UserResponseDto;
import com.example.cleanapi.borders.entities.User;
import com.example.cleanapi.borders.interfaces.UserRepository;
import com.example.cleanapi.borders.validators.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserUseCaseImpl
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("User Use Case Tests")
class UserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator userValidator;

    private UserUseCaseImpl userUseCase;

    @BeforeEach
    void setUp() {
        userUseCase = new UserUseCaseImpl(userRepository, userValidator);
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {
        // Arrange
        CreateUserDto createDto = new CreateUserDto("John Doe", "john.doe@example.com");
        User savedUser = new User(1L, "John Doe", "john.doe@example.com", 
                                 LocalDateTime.now(), LocalDateTime.now());
        
        UserValidator.ValidationResult validResult = 
            new UserValidator.ValidationResult(true, List.of());

        when(userValidator.validateCreateUser(createDto)).thenReturn(validResult);
        when(userRepository.existsByEmail(createDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponseDto result = userUseCase.createUser(createDto);

        // Assert
        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getName(), result.getName());
        assertEquals(savedUser.getEmail(), result.getEmail());
        
        verify(userValidator).validateCreateUser(createDto);
        verify(userRepository).existsByEmail(createDto.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when validation fails")
    void shouldThrowExceptionWhenValidationFails() {
        // Arrange
        CreateUserDto createDto = new CreateUserDto("", "invalid-email");
        UserValidator.ValidationResult invalidResult = 
            new UserValidator.ValidationResult(false, Arrays.asList("Name is required", "Email format is invalid"));

        when(userValidator.validateCreateUser(createDto)).thenReturn(invalidResult);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userUseCase.createUser(createDto)
        );
        assertTrue(exception.getMessage().contains("Validation failed"));
        
        verify(userValidator).validateCreateUser(createDto);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        CreateUserDto createDto = new CreateUserDto("John Doe", "john.doe@example.com");
        UserValidator.ValidationResult validResult = 
            new UserValidator.ValidationResult(true, List.of());

        when(userValidator.validateCreateUser(createDto)).thenReturn(validResult);
        when(userRepository.existsByEmail(createDto.getEmail())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userUseCase.createUser(createDto)
        );
        assertEquals("User with this email already exists", exception.getMessage());
        
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should get user by ID successfully")
    void shouldGetUserByIdSuccessfully() {
        // Arrange
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john.doe@example.com", 
                           LocalDateTime.now(), LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserResponseDto result = userUseCase.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        
        verify(userRepository).findById(userId);
    }

    @Test
    @DisplayName("Should throw exception when user not found by ID")
    void shouldThrowExceptionWhenUserNotFoundById() {
        // Arrange
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userUseCase.getUserById(userId)
        );
        assertTrue(exception.getMessage().contains("User not found with ID"));
        
        verify(userRepository).findById(userId);
    }

    @Test
    @DisplayName("Should get all users successfully")
    void shouldGetAllUsersSuccessfully() {
        // Arrange
        List<User> users = Arrays.asList(
            new User(1L, "John Doe", "john.doe@example.com", LocalDateTime.now(), LocalDateTime.now()),
            new User(2L, "Jane Doe", "jane.doe@example.com", LocalDateTime.now(), LocalDateTime.now())
        );

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserResponseDto> result = userUseCase.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(users.get(0).getName(), result.get(0).getName());
        assertEquals(users.get(1).getName(), result.get(1).getName());
        
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserSuccessfully() {
        // Arrange
        Long userId = 1L;
        UpdateUserDto updateDto = new UpdateUserDto("Jane Doe", "jane.doe@example.com");
        User existingUser = new User(userId, "John Doe", "john.doe@example.com", 
                                   LocalDateTime.now(), LocalDateTime.now());
        User updatedUser = new User(userId, "Jane Doe", "jane.doe@example.com", 
                                  LocalDateTime.now(), LocalDateTime.now());
        
        UserValidator.ValidationResult validResult = 
            new UserValidator.ValidationResult(true, List.of());

        when(userValidator.validateUpdateUser(updateDto)).thenReturn(validResult);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByEmail(updateDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Act
        UserResponseDto result = userUseCase.updateUser(userId, updateDto);

        // Assert
        assertNotNull(result);
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        
        verify(userValidator).validateUpdateUser(updateDto);
        verify(userRepository).findById(userId);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUserSuccessfully() {
        // Arrange
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.deleteById(userId)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> userUseCase.deleteUser(userId));

        // Assert
        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent user")
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        // Arrange
        Long userId = 999L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userUseCase.deleteUser(userId)
        );
        assertTrue(exception.getMessage().contains("User not found with ID"));
        
        verify(userRepository).existsById(userId);
        verify(userRepository, never()).deleteById(userId);
    }
}
