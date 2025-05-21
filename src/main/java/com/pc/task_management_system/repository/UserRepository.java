package com.pc.task_management_system.repository;

import com.pc.task_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
}
