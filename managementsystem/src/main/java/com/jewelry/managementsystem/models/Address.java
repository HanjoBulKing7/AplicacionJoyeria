package com.jewelry.managementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.aspectj.bridge.IMessage;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @Column(nullable = false,  length = 70)
    private String street;
    @Column(nullable = false,  length = 70)
    private String neighborhood;
    @Column(nullable = false,  length = 25)
    private String city;
    @Column(nullable = false,  length = 25)
    private String state;
    @Column(nullable = false,  length = 20)
    private String country;
    @Column(nullable = false,  length = 10)
    private String zipCode;

    @ToString.Exclude
    @JoinColumn(name="user_id")
    @ManyToOne
    private User user;

}
