package com.monstarlab.bookmanagement.repository;

import com.monstarlab.bookmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findDistinctByUsername(String username);
}
