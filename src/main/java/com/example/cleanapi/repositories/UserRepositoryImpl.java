package com.example.cleanapi.repositories;

import com.example.cleanapi.borders.entities.User;
import com.example.cleanapi.borders.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of UserRepository interface using Spring Data JPA
 * This adapter converts between domain entities and JPA entities
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private final UserJpaRepository jpaRepository;
    
    @Autowired
    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public User save(User user) {
        UserJpaEntity jpaEntity = mapToJpaEntity(user);
        UserJpaEntity savedEntity = jpaRepository.save(jpaEntity);
        return mapToDomainEntity(savedEntity);
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                           .map(this::mapToDomainEntity);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                           .map(this::mapToDomainEntity);
    }
    
    @Override
    public List<User> findAll() {
        return jpaRepository.findAll()
                           .stream()
                           .map(this::mapToDomainEntity)
                           .collect(Collectors.toList());
    }
    
    @Override
    public boolean deleteById(Long id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
    
    /**
     * Convert domain entity to JPA entity
     */
    private UserJpaEntity mapToJpaEntity(User user) {
        UserJpaEntity jpaEntity = new UserJpaEntity(
            user.getName(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
        
        if (user.getId() != null) {
            jpaEntity.setId(user.getId());
        }
        
        return jpaEntity;
    }
    
    /**
     * Convert JPA entity to domain entity
     */
    private User mapToDomainEntity(UserJpaEntity jpaEntity) {
        return new User(
            jpaEntity.getId(),
            jpaEntity.getName(),
            jpaEntity.getEmail(),
            jpaEntity.getCreatedAt(),
            jpaEntity.getUpdatedAt()
        );
    }
}
