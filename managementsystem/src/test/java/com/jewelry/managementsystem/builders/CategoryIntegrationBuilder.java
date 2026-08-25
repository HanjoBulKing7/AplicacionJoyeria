package com.jewelry.managementsystem.builders;

import com.jewelry.managementsystem.models.Category;
import com.jewelry.managementsystem.models.Item;

import java.util.Collections;
import java.util.List;

public class CategoryIntegrationBuilder {

        private Long id = null;
        private String name = "Rings";
        private List<Item> items = Collections.emptyList();


        private CategoryIntegrationBuilder() {

        }
        public static CategoryIntegrationBuilder aCategory() {
            return new CategoryIntegrationBuilder();
        }

        public CategoryIntegrationBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        public CategoryIntegrationBuilder withName(String name) {
            this.name = name;
            return this;
        }
        public CategoryIntegrationBuilder withItems(List<Item> items) {
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
