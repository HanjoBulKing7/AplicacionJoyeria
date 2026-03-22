package com.jewelry.managementsystem.mapper;


import com.jewelry.managementsystem.models.CartItem;
import com.jewelry.managementsystem.payload.CartItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper( componentModel = "spring")
public interface CartItemMapper extends GenericMapper <CartItem, CartItemDTO> {

    @Mapping(source = "cartItem.category.name", target = "categoryName")
    CartItemDTO toDto(CartItem cartItem);
}
