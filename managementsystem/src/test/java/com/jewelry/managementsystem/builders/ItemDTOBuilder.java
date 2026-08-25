package com.jewelry.managementsystem.builders;

import com.jewelry.managementsystem.constants.ItemStatus;
import com.jewelry.managementsystem.models.Category;
import com.jewelry.managementsystem.payload.ItemDTO;

public class ItemDTOBuilder {
    private Long itemDTOId = 1L;
    private String name = "Gold Ring";
    private String description = null;
    private Float price = 100.5F;
    private Integer stock = 10;
    private ItemStatus status = ItemStatus.ACTIVE;
    private String image = "imageRing.png";
    private Category category = CategoryBuilder.aCategory().build();

    private ItemDTOBuilder() {}
    public static ItemDTOBuilder anItemDTO() {

        return new ItemDTOBuilder();
    }
    public ItemDTOBuilder withId(Long itemDTOId) {
        this.itemDTOId = itemDTOId;
        return this;
    }
    public ItemDTOBuilder withName(String name) {
        this.name = name;
        return this;
    }
    public ItemDTOBuilder withDescription(String description) {
        this.description = description;
        return this;
    }
    public ItemDTOBuilder withPrice(Float price) {
        this.price = price;
        return this;
    }
    public ItemDTOBuilder withStock(Integer stock) {
        this.stock = stock;
        return this;
    }
    public ItemDTOBuilder withStatus(ItemStatus status) {
        this.status = status;
        return this;
    }
    public ItemDTOBuilder withImage(String image) {
        this.image = image;
        return this;
    }
    public ItemDTOBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }
    public ItemDTO build() {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setProductId(itemDTOId);
            itemDTO.setName(name);
            itemDTO.setDescription(description);
            itemDTO.setPrice(price);
            itemDTO.setStock(stock);
            itemDTO.setStatus(status);
            itemDTO.setImage(image);
            itemDTO.setCategoryId(category.getId());

            return itemDTO;
    }
}
