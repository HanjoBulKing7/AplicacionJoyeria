package com.jewelry.managementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    @Email
    private String email;

    @NotBlank
    @Size(max=120)
    private String password;

    @ManyToMany( cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @OneToOne( mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE } )
    private Cart cart;

    @OneToMany (mappedBy = "user",
                cascade = {CascadeType.PERSIST, CascadeType.MERGE},
                orphanRemoval = true)
    @ToString.Exclude
    private List<Address> addresses = new ArrayList<>();

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
