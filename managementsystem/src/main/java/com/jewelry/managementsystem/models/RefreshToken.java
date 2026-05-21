package com.jewelry.managementsystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn( name = "user_id")
    private User user;

    @Column( nullable = false, unique = true )
    private String token;

    @Column( nullable = false )
    private Instant expirationDate;
}
