package com.jewelry.managementsystem.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated ( EnumType.STRING)
    private Roles rolename;

    public Role(Roles rolename){
        this.rolename = rolename;
    }
}
