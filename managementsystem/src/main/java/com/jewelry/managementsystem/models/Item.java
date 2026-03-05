package com.jewelry.managementsystem.models;

import com.jewelry.managementsystem.constants.ItemStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {
     @Id
     @GeneratedValue ( strategy = GenerationType.IDENTITY)
     private Long id;

     @NotBlank ( message = "Please send a valid name for the item" )
     @Size ( min = 1, max = 15 )
    private String name;

     @Nullable
     @Size(min = 1, max = 100)
     private String description;

     @NotNull ( message = "Price is mandatory for each item")
     @Positive ( message = "Price must be greater than  0")
     private Float price;

     @NotNull
     @PositiveOrZero ( message = "Stock must be greater or 0 ")
    private Integer stock;

     @Enumerated ( EnumType.STRING )
     @NotNull  (  message = "Item status is mandatory")
      private ItemStatus status;

}
