package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.constants.DefaultValues;
import com.jewelry.managementsystem.payload.APIResponse;
import com.jewelry.managementsystem.payload.CategoryDTO;
import com.jewelry.managementsystem.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api" )
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping ( "/public/categories/{id}" )
    public ResponseEntity<CategoryDTO>  getCategory( @PathVariable Long id )
    {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);

        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @GetMapping ( "/public/categories" )
    public ResponseEntity<APIResponse>  getAllCategories(
                                                        @RequestParam( name = "pageNumber" , defaultValue = DefaultValues.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                                                        @RequestParam ( name = "pageSize" , defaultValue = DefaultValues.DEFAULT_PAGE_SIZE , required = false) Integer pageSize,
                                                        @RequestParam ( name = "sortBy", defaultValue = DefaultValues.DEFAULT_SORT_FIELD , required = false )  String sortBy,
                                                        @RequestParam ( name = "sortOrder", defaultValue = DefaultValues.DEFAULT_SORT_ORDER, required = false ) String sortOrder
                                                        )
    {

        APIResponse<CategoryDTO> apiResponse = categoryService.getAllCategories( pageNumber, pageSize, sortBy, sortOrder );

        return new ResponseEntity<>( apiResponse, HttpStatus.OK );
    }

    @PostMapping ( "/admin/categories" )
    public ResponseEntity<CategoryDTO>   addCategory( @Valid @RequestBody CategoryDTO categoryDTO )
    {
        CategoryDTO createdCategoryDTO =  categoryService.createCategory( categoryDTO );

        return new ResponseEntity<>( createdCategoryDTO, HttpStatus.CREATED );

    }

    @PutMapping ( "/admin/categories/{id}" )
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
                                                      @Valid @RequestBody CategoryDTO categoryDTO ){

            CategoryDTO updatedCategoryDTO =  categoryService.updateCategory( id, categoryDTO );

            return new ResponseEntity<>( updatedCategoryDTO, HttpStatus.OK );
    }

    @DeleteMapping ( "/admin/categories/{id}" )
    public ResponseEntity<CategoryDTO> deleteCategory( @PathVariable Long id ){

        CategoryDTO updatedCategoryDTO =  categoryService.deleteCategory( id );

        return new ResponseEntity<>( updatedCategoryDTO, HttpStatus.OK );
    }

}
