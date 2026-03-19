package com.jewelry.managementsystem.mapper;

import com.jewelry.managementsystem.models.Cart;
import com.jewelry.managementsystem.payload.CartDTO;
import com.jewelry.managementsystem.payload.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper extends GenericMapper <Cart, CartDTO> {
}
