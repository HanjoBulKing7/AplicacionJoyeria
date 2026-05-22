package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.RefreshToken;
import com.jewelry.managementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser_UserId(Long userId);

    Optional<RefreshToken> findByToken(String refreshToken);

    boolean existsByUser(User user);
}
