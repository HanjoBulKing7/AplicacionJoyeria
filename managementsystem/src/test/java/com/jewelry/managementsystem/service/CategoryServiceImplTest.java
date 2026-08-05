package com.jewelry.managementsystem.service;

import com.jewelry.managementsystem.builders.CategoryBuilder;
import com.jewelry.managementsystem.builders.CategoryDTOBuilder;
import com.jewelry.managementsystem.exceptions.DuplicateResourceException;
import com.jewelry.managementsystem.mapper.CategoryMapper;
import com.jewelry.managementsystem.models.Category;
import com.jewelry.managementsystem.payload.APIResponse;
import com.jewelry.managementsystem.payload.CategoryDTO;
import com.jewelry.managementsystem.repositories.CategoryRepository;
import com.jewelry.managementsystem.services.CategoryServiceImpl;
import net.bytebuddy.description.type.TypeList;
import org.junit.jupiter.api.*;
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
@Disabled
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
        testCategory = CategoryBuilder.aCategory().build();
        testCategoryDTO = CategoryDTOBuilder.aCategoryDTO().build();

    }

    // ---- GET CATEGORY --------------------------------------------------------
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

    // ------- CREATE CATEGORY -------------------------
    @Test
    @DisplayName("Create a category - happy path")
    void  createCategory_Sucess(){
        ///  Arrange
        CategoryDTO inputCategory = CategoryDTOBuilder.aCategoryDTO().withName("Chains").build();
        Category savedCategory = CategoryBuilder.aCategory().withName("Chains").build();
        CategoryDTO expectedDTO = CategoryDTOBuilder.aCategoryDTO().withName("Chains").build();

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryMapper.toEntity(any())).thenReturn(savedCategory);
        when(categoryRepository.save(any())).thenReturn(savedCategory);
        when(categoryMapper.toDto(any())).thenReturn(expectedDTO);
        ///  Act
        CategoryDTO res = categoryService.createCategory(inputCategory);
        ///  Assert
        Assertions.assertNotNull(res);
        Assertions.assertEquals(expectedDTO.getId(),res.getId());
        verify(categoryRepository, times(1)).save(savedCategory);
        verify(categoryMapper, times(1)).toDto(any());
        verify(categoryMapper, times(1)).toEntity(any());

    }

    @Test
    @DisplayName("Create category - sad path")
    void createCategoryThrowsException(){
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(testCategory));

        DuplicateResourceException duplicateExc = Assertions.assertThrows(DuplicateResourceException.class,
                ()->categoryService.createCategory(testCategoryDTO)
        );

        Assertions.assertEquals("Category with category name: Rings, already exists", duplicateExc.getMessage());
        verify( categoryRepository, times(1)).findByName(anyString());
        verify(categoryRepository, never()).save(any());
    }

    /// ---- UPDATE CATEGORY -----------------------------------------------------------------
    @Test
    @DisplayName("Update a category - happy path ")
    void updateCategory_Success(){
        CategoryDTO requestDTO = CategoryDTOBuilder.aCategoryDTO().withName("Chains").build();
        CategoryDTO updatedCategory = CategoryDTOBuilder
                .aCategoryDTO()
                        .withName("Chains")
                                .withId(1L).build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.existsByNameAndIdNot(requestDTO.getName(), 1L)).thenReturn(Boolean.FALSE);
        when(categoryRepository.save(testCategory)).thenReturn(testCategory);
        when(categoryMapper.toDto(testCategory)).thenReturn(updatedCategory);

        CategoryDTO result = categoryService.updateCategory(1L,  requestDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L  ,result.getId());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).existsByNameAndIdNot(requestDTO.getName(), 1L);
        verify(categoryMapper, times(1)).updateFromDto(requestDTO, testCategory);

    }
    @Test
    @DisplayName("Update category - Sad path: No category found with Id")
    void updateCategoryNotFound(){
        CategoryDTO requestDTo = CategoryDTOBuilder.aCategoryDTO().withName("Earrings").build();

        when(categoryRepository.findById(any())).thenReturn(Optional.empty());
        EmptyResourceException emptyEx = Assertions.assertThrows(EmptyResourceException.class,
                () -> categoryService.updateCategory(2L,  requestDTo));

        Assertions.assertEquals("No category with id: 2 found", emptyEx.getMessage());
        verify(categoryRepository, times(1)).findById(2L);
        verify(categoryRepository, never()).existsByNameAndIdNot(requestDTo.getName(), 2L);
    }

    @Test
    @DisplayName(" Update category - Sad Path: Category throws duplicate exception")
    void  updateCategoryDuplicateException(){
        CategoryDTO requestDTO = CategoryDTOBuilder.aCategoryDTO().withName("Chains").build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.existsByNameAndIdNot(requestDTO.getName(), 1L)).thenReturn(Boolean.TRUE);

        DuplicateResourceException duplicateEx =  Assertions.assertThrows(DuplicateResourceException.class,
                () -> categoryService.updateCategory(1L, requestDTO));

        Assertions.assertEquals("Category with category name: "+requestDTO.getName()+", already exists", duplicateEx.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryMapper, never()).updateFromDto(requestDTO, testCategory);
    }

    // ------------ DELETE CATEGORY -------------------------------------------------
    @Test
    @DisplayName("Delete category - Happy path ")
    void deleteCategory_Success(){

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryMapper.toDto(testCategory)).thenReturn(testCategoryDTO);

        CategoryDTO deletedFromService = categoryService.deleteCategory(1L);

        Assertions.assertNotNull(deletedFromService.getId());
        Assertions.assertEquals(testCategoryDTO.getId(),deletedFromService.getId());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).delete(testCategory);
    }

    @Test
    @DisplayName("Delete category - Sad path: No found category ")
    void deleteCategoryNotFound(){
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        EmptyResourceException emptyEx =  Assertions.assertThrows(EmptyResourceException.class,
                () -> categoryService.deleteCategory(1L));

        Assertions.assertEquals("No category with id: 1 found", emptyEx.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, never()).delete(any());
    }
}
