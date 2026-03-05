package com.jewelry.managementsystem.mapper;

import com.jewelry.managementsystem.models.Item;
import com.jewelry.managementsystem.payload.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper ( componentModel = "spring" )
public interface ItemMapper extends GenericMapper<Item, ItemDTO>{

    ///  Since this class inherits all the methods from the interface of MapStruct we do not need to implement anything

}