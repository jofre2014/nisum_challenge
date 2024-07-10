package com.nisum.challenge.repository;

import com.nisum.challenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByIsActiveTrue();

    Optional<User> findByIdAndIsActiveTrue(UUID id);

    Optional<User> findByEmailAndIsActiveTrue(String email);

}
