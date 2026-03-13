package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(@NotBlank @Size( max = 50) String username);

    boolean existsByEmail(@NotBlank @Size( max = 50) @Email String email);
}
