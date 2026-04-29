package com.jewelry.managementsystem.mapper;

import com.jewelry.managementsystem.models.CartItem;
import com.jewelry.managementsystem.models.Item;
import com.jewelry.managementsystem.payload.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper ( componentModel = "spring" )
public interface ItemMapper extends GenericMapper<Item, ItemDTO>{


    @Override ///  Since this class inherits all the methods from the interface of MapStruct we do not need to implement anything
    @Mapping(source = "category.id", target = "categoryId")
    ItemDTO toDto(Item item);

    @Override
    @Mapping(target = "id", ignore = true) // <--- Evita que Hibernate explote
    void updateFromDto(ItemDTO dto, @MappingTarget Item existingItem);

    CartItem toCartItem(Item item); /// To add products to the car
}