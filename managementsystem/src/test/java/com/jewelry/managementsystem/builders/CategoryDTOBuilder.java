package com.jewelry.managementsystem.builders;

import com.jewelry.managementsystem.payload.CategoryDTO;
import com.jewelry.managementsystem.payload.ItemDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryDTOBuilder {

    private Long id = 1L;
    private String name = "Rings";
    private List<ItemDTO> itemsDTO = Collections.emptyList();

    private CategoryDTOBuilder(){}
    public static CategoryDTOBuilder aCategoryDTO(){
        return new CategoryDTOBuilder();
    }
    public CategoryDTOBuilder withId(Long id){
        this.id = id;
        return this;
    }
    public CategoryDTOBuilder withName(String name){
        this.name = name;
        return this;
    }
    public CategoryDTOBuilder withItemsDTO(List<ItemDTO> itemDTOList){
        this.itemsDTO = itemDTOList;
        return this;
    }
    public CategoryDTO build(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        categoryDTO.setName(name);
        return categoryDTO;
    }

}