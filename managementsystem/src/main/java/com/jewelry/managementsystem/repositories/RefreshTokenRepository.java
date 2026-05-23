package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.RefreshToken;
import com.jewelry.managementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser_UserId(Long userId);

    Optional<RefreshToken> findByToken(String refreshToken);

    boolean existsByUser(User user);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.token = :token")
    Integer deleteByToken(@Param("token") String issuedToken);
}
