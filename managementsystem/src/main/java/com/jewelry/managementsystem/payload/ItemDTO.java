package com.jewelry.managementsystem.payload;

import com.jewelry.managementsystem.constants.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private String name;
    private String description;
    private Float price;
    private Integer stock;
    private ItemStatus status;

}
