package com.jewelry.managementsystem.mapper;


import com.jewelry.managementsystem.models.CartItem;
import com.jewelry.managementsystem.payload.CartItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper( componentModel = "spring")
public interface CartItemMapper extends GenericMapper <CartItem, CartItemDTO> {


    @Mapping(source = "originalItem.id", target = "productId")
    CartItemDTO toDto(CartItem cartItem);
}
