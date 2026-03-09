package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.payload.APIResponse;
import com.jewelry.managementsystem.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO getCategoryById(Long id);
    APIResponse<CategoryDTO> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    CategoryDTO  createCategory(CategoryDTO categoryDTO);
    CategoryDTO  updateCategory(Long id, CategoryDTO categoryDTO);
    CategoryDTO  deleteCategory(Long id);
}