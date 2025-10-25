package com.example.cleanapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository interface
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    
    Optional<UserJpaEntity> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
