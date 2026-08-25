package com.jewelry.managementsystem.builders;

import com.jewelry.managementsystem.constants.ItemStatus;
import com.jewelry.managementsystem.models.Category;
import com.jewelry.managementsystem.models.Item;

public class ItemBuilder {

    private Long id = 1L;
    private String name = "Gold Ring";
    private String description = null;
    private Float price = 100.5F;
    private Integer stock = 10;
    private ItemStatus status = ItemStatus.ACTIVE;
    private String image = "imageRing.png";
    private Category category = CategoryBuilder.aCategory().build();


    private ItemBuilder(){

    }
    public static ItemBuilder anItem(){
        return new ItemBuilder();
    }
    public ItemBuilder withId(Long id){
        this.id = id;
        return this;
    }
    public ItemBuilder withName(String name){
        this.name = name;
        return this;
    }
    public ItemBuilder withDescription(String description){
        this.description = description;
        return this;
    }
    public ItemBuilder withPrice(Float price){
        this.price = price;
        return this;
    }
    public ItemBuilder withStock(Integer stock){
        this.stock = stock;
        return this;
    }
    public ItemBuilder withStatus(ItemStatus status){
        this.status = status;
        return this;
    }
    public ItemBuilder withImage(String image){
        this.image = image;
        return this;
    }
    public ItemBuilder withCategory(Category category){
        this.category = category;
        return this;
    }
    public ItemBuilder inactive(){
        this.status = ItemStatus.INACTIVE;
        return this;
    }

    public Item build() {

        Item item = new Item();

        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setStock(stock);
        item.setStatus(status);
        item.setImage(image);
        item.setCategory(category);

        return item;
    }

}
