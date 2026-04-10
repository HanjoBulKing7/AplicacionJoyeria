package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.constants.DefaultValues;
import com.jewelry.managementsystem.payload.APIResponse;
import com.jewelry.managementsystem.payload.CategoryDTO;
import com.jewelry.managementsystem.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category", description = "Endpoints for managing jewelry categories and catalog organization")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get category by ID", description = "Retrieves a single category detail using its unique ID.")
    @GetMapping("/public/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get all categories", description = "Retrieves a paginated list of categories with support for custom sorting.")
    @GetMapping("/public/categories")
    public ResponseEntity<APIResponse> getAllCategories(
            @Parameter(description = "Page number to retrieve") @RequestParam(name = "pageNumber", defaultValue = DefaultValues.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @Parameter(description = "Number of items per page") @RequestParam(name = "pageSize", defaultValue = DefaultValues.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @Parameter(description = "Field to sort the results by") @RequestParam(name = "sortBy", defaultValue = DefaultValues.DEFAULT_SORT_FIELD, required = false) String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(name = "sortOrder", defaultValue = DefaultValues.DEFAULT_SORT_ORDER, required = false) String sortOrder
    ) {
        APIResponse<CategoryDTO> apiResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Create category", description = "Registers a new jewelry category. Admin access required.")
    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategoryDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Update category", description = "Updates an existing category name or details by ID. Admin access required.")
    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
                                                      @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategoryDTO = categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
    }

    @Operation(summary = "Delete category", description = "Permanently removes a category from the system. Admin access required.")
    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        CategoryDTO updatedCategoryDTO = categoryService.deleteCategory(id);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
    }
}