package com.jewelry.managementsystem.builders;

import com.jewelry.managementsystem.models.Category;
import com.jewelry.managementsystem.models.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryBuilder {


    private Long id = 1L;
    private String name = "Rings";
    private List<Item> items = Collections.emptyList();


    private CategoryBuilder() {

    }
    public static CategoryBuilder aCategory() {
        return new CategoryBuilder();
    }

    public CategoryBuilder withId(Long id) {
        this.id = id;
        return this;
    }
    public CategoryBuilder withName(String name) {
        this.name = name;
        return this;
    }
    public CategoryBuilder withItems(List<Item> items) {
        this.items = items;
        return this;
    }
    public Category build() {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setItems(items);
        return category;
    }

}
