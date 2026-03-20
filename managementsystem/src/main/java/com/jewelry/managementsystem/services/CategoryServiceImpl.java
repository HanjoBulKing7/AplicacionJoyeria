package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.exceptions.DuplicateResourceException;
import com.jewelry.managementsystem.exceptions.EmptyResourceException;
import com.jewelry.managementsystem.mapper.CategoryMapper;
import com.jewelry.managementsystem.models.Category;
import com.jewelry.managementsystem.payload.APIResponse;
import com.jewelry.managementsystem.payload.CategoryDTO;
import com.jewelry.managementsystem.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO getCategoryById(Long id) {

        return categoryMapper.toDto(
                categoryRepository.findById(id)
                        .orElseThrow( () -> new EmptyResourceException( id , "Category"))   );
    }

    @Override
    public APIResponse<CategoryDTO> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categoryList = categoryPage.getContent();

        if (categoryList.isEmpty()) throw new EmptyResourceException("category");

        APIResponse<CategoryDTO> categoryResponse = new APIResponse<>();

        categoryResponse.setContent(
                categoryList
                        .stream().map(
                                categoryMapper::toDto
                        ).toList()
        );

        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());


        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        categoryRepository.findByName(categoryDTO.getName())
                .ifPresent( (existingCategory) -> {throw new DuplicateResourceException("Category", "category name",  categoryDTO.getName());
                });

        Category category = categoryMapper.toEntity(categoryDTO);
        Category categorySaved =  categoryRepository.save(category);

        return  categoryMapper.toDto(categorySaved);

    }

    @Override
    public CategoryDTO updateCategory(Long id,CategoryDTO categoryDTO) {

        Category existingCategory = categoryRepository.findById(id)
                        .orElseThrow( () -> new EmptyResourceException( id , "category"));

        if( categoryRepository.existsByNameAndIdNot(categoryDTO.getName(), id) ) throw new DuplicateResourceException("Category", "category name",  categoryDTO.getName());

        categoryMapper.updateFromDto(categoryDTO, existingCategory);

        Category savedCategory = categoryRepository.save(existingCategory);

        return   categoryMapper.toDto(savedCategory);

    }

    @Transactional
    @Override
    public CategoryDTO deleteCategory(Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow( () -> new EmptyResourceException( id , "category"));

        categoryRepository.delete(existingCategory);

        return  categoryMapper.toDto(existingCategory);

    }
}
