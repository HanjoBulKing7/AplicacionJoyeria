package com.jewelry.managementsystem.mapper;

import com.jewelry.managementsystem.models.Category;
import com.jewelry.managementsystem.payload.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper ( componentModel = "spring" )
public interface CategoryMapper extends GenericMapper <Category, CategoryDTO> {


}
