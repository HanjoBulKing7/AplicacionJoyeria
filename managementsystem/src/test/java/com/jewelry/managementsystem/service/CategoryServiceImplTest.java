package com.jewelry.managementsystem.service;

import com.jewelry.managementsystem.exceptions.DuplicateResourceException;
import com.jewelry.managementsystem.mapper.CategoryMapper;
import com.jewelry.managementsystem.models.Category;
import com.jewelry.managementsystem.payload.APIResponse;
import com.jewelry.managementsystem.payload.CategoryDTO;
import com.jewelry.managementsystem.repositories.CategoryRepository;
import com.jewelry.managementsystem.services.CategoryServiceImpl;
import net.bytebuddy.description.type.TypeList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.jewelry.managementsystem.exceptions.EmptyResourceException;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;


    Category testCategory;
    CategoryDTO testCategoryDTO;


    @BeforeEach
    void setUp(){
        /// Dummy category object for List
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Rings");
        testCategory.setItems(Collections.emptyList());
        ///  Dummy categoryDTO object
        testCategoryDTO = new CategoryDTO();
        testCategoryDTO.setId(1L);
        testCategoryDTO.setName("Rings");
        testCategoryDTO.setItems(Collections.emptyList());

    }

    @Test
    @DisplayName("Get one category")
    void getCategoryById_Sucess(){
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryMapper.toDto(testCategory)).thenReturn(testCategoryDTO);

        CategoryDTO categoryDTO = categoryService.getCategoryById(1L);

        Assertions.assertNotNull(categoryDTO);
        Assertions.assertEquals(testCategoryDTO.getId(),categoryDTO.getId());

        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get category exception")
    void getCategoryThrowsException(){

        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        EmptyResourceException emptyE =
                Assertions.assertThrows(EmptyResourceException.class,
                        () -> categoryService.getCategoryById(999L));

        Assertions.assertEquals(999L, emptyE.getId());
        Assertions.assertEquals("Category",emptyE.getResourceName());

        verify(categoryRepository, times(1)).findById(999L);
    }


    @Test
    @DisplayName("Get all categories")
    void getAllCategories_Succes() {
        ///  Arrange
        Integer pageNumber = 0;
        Integer pageSize = 10;
        String sortBy = "id";
        String sortDir = "asc";
        List<Category> categoryList = List.of(testCategory);
        Sort sortByDir = Sort.by(sortBy).ascending();
        Pageable testDetails = PageRequest.of(pageNumber, pageSize, sortByDir);
        Page<Category> categoryPage = new PageImpl<>(categoryList, testDetails, categoryList.size());
        when(categoryMapper.toDto(testCategory)).thenReturn(testCategoryDTO);
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);

        ///  Act
        APIResponse testResponseGetAll = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);

        /// Assert
        Assertions.assertNotNull(testResponseGetAll);
        Assertions.assertEquals(pageNumber, testResponseGetAll.getPageNumber() );
        Assertions.assertEquals(pageSize, testResponseGetAll.getPageSize());
        Assertions.assertEquals(categoryList.size(), testResponseGetAll.getContent().size());
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
        verify(categoryMapper, times(categoryList.size())).toDto(testCategory);
    }

    @Test
    @DisplayName("Get all throws empty exception")
    void getAllThrowsException() {
        ///  Arrange
        Page<Category> categoryEmptyPage = new PageImpl<>(Collections.emptyList());
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryEmptyPage);
        ///  Act
        EmptyResourceException caughtException = Assertions.assertThrows(
                EmptyResourceException.class,
                () -> categoryService.getAllCategories(0, 10, "id", "asc")
        );
        ///  Assert
        Assertions.assertEquals("No category found", caughtException.getMessage());
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));

    }

    @Test
    @DisplayName("Create a category")
    void  createCategory_Sucess(){
        ///  Arrange
        CategoryDTO inputDTO = new CategoryDTO(null, "Chains", Collections.emptyList());
        Category entityBeforeSaving = new Category(null, "Chains", Collections.emptyList());
        Category entitySaved = new  Category(1L, "Chains", Collections.emptyList());
        CategoryDTO resultDTO = new  CategoryDTO(1L, "Chains", Collections.emptyList());
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryMapper.toEntity(inputDTO)).thenReturn(entityBeforeSaving);
        when(categoryRepository.save(any())).thenReturn(entitySaved);
        when(categoryMapper.toDto(entitySaved)).thenReturn(resultDTO);
        ///  Act
        CategoryDTO serviceResultDTO = categoryService.createCategory(inputDTO);
        ///  Assert
        Assertions.assertNotNull(serviceResultDTO);
        Assertions.assertEquals(resultDTO.getId(),serviceResultDTO.getId());
        verify(categoryRepository, times(1)).save(entityBeforeSaving);
        verify(categoryMapper, times(1)).toDto(entitySaved);
        verify(categoryMapper, times(1)).toEntity(inputDTO);

    }

    @Test
    @DisplayName("Create category fails")
    void createCategoryThrowsException(){
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(testCategory));

        DuplicateResourceException duplicateExc = Assertions.assertThrows(DuplicateResourceException.class,
                ()->categoryService.createCategory(testCategoryDTO)
        );

        Assertions.assertEquals("Category with category name: Rings, already exists", duplicateExc.getMessage());
        verify( categoryRepository, times(1)).findByName(anyString());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Update a category")
    void updateCategory_Success(){
        CategoryDTO inputDTO = new CategoryDTO(null, "Chains", Collections.emptyList());
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.existsByNameAndIdNot(inputDTO.getName(), 1L)).thenReturn(Boolean.FALSE);

        when(categoryRepository.save(testCategory)).thenReturn(testCategory);

        CategoryDTO categoryDTOUpdated = new CategoryDTO(1L, "Chains", Collections.emptyList());
        when(categoryMapper.toDto(testCategory)).thenReturn(categoryDTOUpdated);

        CategoryDTO result = categoryService.updateCategory(1L,  inputDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(categoryDTOUpdated.getId(),result.getId());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).existsByNameAndIdNot(inputDTO.getName(), 1L);
        verify(categoryMapper, times(1)).updateFromDto(inputDTO, testCategory);

    }
    @Test
    @DisplayName("No category found with Id")
    void updateCategoryNotFound(){
        CategoryDTO inputDTO = new CategoryDTO(null, "Chains", Collections.emptyList());
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        EmptyResourceException emptyEx = Assertions.assertThrows(EmptyResourceException.class,
                () -> categoryService.updateCategory(1L,  inputDTO));

        Assertions.assertEquals("No category with id: 1 found", emptyEx.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, never()).existsByNameAndIdNot(inputDTO.getName(), 1L);
    }

    @Test
    @DisplayName("Category throws duplicate exception")
    void  updateCategoryDuplicateException(){
        CategoryDTO inputDTO = new CategoryDTO(null, "Chains", Collections.emptyList());
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.existsByNameAndIdNot(inputDTO.getName(), 1L)).thenReturn(Boolean.TRUE);

        DuplicateResourceException duplicateEx =  Assertions.assertThrows(DuplicateResourceException.class,
                () -> categoryService.updateCategory(1L, inputDTO));

        Assertions.assertEquals("Category with category name: "+inputDTO.getName()+", already exists", duplicateEx.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryMapper, never()).updateFromDto(inputDTO, testCategory);
    }

    @Test
    @DisplayName("Delete successfully")
    void deleteCategory_Success(){
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryMapper.toDto(testCategory)).thenReturn(testCategoryDTO);

        CategoryDTO deletedFromService = categoryService.deleteCategory(1L);

        Assertions.assertNotNull(deletedFromService);
        Assertions.assertEquals(testCategoryDTO.getId(),deletedFromService.getId());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).delete(testCategory);
    }

    @Test
    @DisplayName("Can't be deleted exception")
    void deleteCategoryNotFound(){
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        EmptyResourceException emptyEx =  Assertions.assertThrows(EmptyResourceException.class,
                () -> categoryService.deleteCategory(1L));

        Assertions.assertEquals("No category with id: 1 found", emptyEx.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, never()).delete(any());
    }
}
