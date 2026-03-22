package com.jewelry.managementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY )
    private Long id;
    @Column(unique = true,  nullable = false)
    private String name;

    @OneToMany ( mappedBy = "category", fetch =  FetchType.LAZY)
    private List<Item> items;

}
