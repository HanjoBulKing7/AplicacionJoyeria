package com.jewelry.managementsystem.service;

import com.jewelry.managementsystem.builders.CategoryBuilder;
import com.jewelry.managementsystem.builders.ItemBuilder;
import com.jewelry.managementsystem.builders.ItemDTOBuilder;
import com.jewelry.managementsystem.constants.ItemStatus;
import com.jewelry.managementsystem.exceptions.DuplicateResourceException;
import com.jewelry.managementsystem.exceptions.EmptyResourceException;
import com.jewelry.managementsystem.factory.TestDataFactory;
import com.jewelry.managementsystem.mapper.ItemMapper;
import com.jewelry.managementsystem.models.Category;
import com.jewelry.managementsystem.models.Item;
import com.jewelry.managementsystem.payload.APIResponse;
import com.jewelry.managementsystem.payload.ItemDTO;
import com.jewelry.managementsystem.repositories.CategoryRepository;
import com.jewelry.managementsystem.repositories.ItemRepository;
import com.jewelry.managementsystem.services.ItemServiceImpl;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ItemMapper itemMapper;

    Item existingItem;
    ItemDTO existingItemDTO;
    Category ringsCategory;

    @BeforeEach
    public void setUp() {
        ringsCategory  = CategoryBuilder.aCategory().build();
        existingItem   = ItemBuilder.anItem().build();
        existingItemDTO = ItemDTOBuilder.anItemDTO().build();
    }

    // ─── GET ONE ITEM ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Get one item — happy path")
    void getItem_Success() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(itemMapper.toDto(existingItem)).thenReturn(existingItemDTO);

        ItemDTO result = itemService.getItem(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingItemDTO.getName(), result.getName());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemMapper,     times(1)).toDto(existingItem);
    }

    @Test
    @DisplayName("Get one item — sad path: item not found")
    void getItem_NotFound() {
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        EmptyResourceException ex = Assertions.assertThrows(
                EmptyResourceException.class,
                () -> itemService.getItem(999L)
        );

        Assertions.assertEquals("No item with id: 999 found", ex.getMessage());
        Assertions.assertEquals(999L, ex.getId());
        verify(itemRepository, times(1)).findById(999L);
        verify(itemMapper,     never()).toDto(any());
    }

    // ─── GET ALL ITEMS ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("Get all items — happy path")
    void getAllItems_Success() {
        List<Item> itemList = List.of(existingItem);
        Page<Item> itemPage = TestDataFactory.page(itemList);

        when(itemRepository.findAll(any(Pageable.class))).thenReturn(itemPage);
        when(itemMapper.toDto(existingItem)).thenReturn(existingItemDTO);

        APIResponse<ItemDTO> result = itemService.getItems(0, 10, "id", "asc");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(itemList.size(), result.getContent().size());
        verify(itemRepository, times(1)).findAll(any(Pageable.class));
        verify(itemMapper,     times(itemList.size())).toDto(existingItem);
    }

    @Test
    @DisplayName("Get all items — sad path: no items in DB")
    void getAllItems_Empty() {
        when(itemRepository.findAll(any(Pageable.class))).thenReturn(TestDataFactory.emptyPage());

        APIResponse res =itemService.getItems(0, 10, "id", "asc");

        Assertions.assertEquals(0,  res.getContent().size());
        verify(itemRepository, times(1)).findAll(any(Pageable.class));
        verify(itemMapper,     never()).toDto(any());
    }

    // ─── GET BY CATEGORY ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Get by category — happy path")
    void getByCategory_Success() {
        List<Item> itemList = List.of(existingItem);
        Page<Item> itemPage = TestDataFactory.page(itemList);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(ringsCategory));
        when(itemRepository.findByCategoryId(anyLong(), any(Pageable.class))).thenReturn(itemPage);
        when(itemMapper.toDto(existingItem)).thenReturn(existingItemDTO);

        APIResponse<ItemDTO> result = itemService.getItemsByCategory(1L, 0, 10, "id", "asc");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(itemList.size(), result.getContent().size());
        verify(categoryRepository, times(1)).findById(1L);
        verify(itemRepository,     times(1)).findByCategoryId(anyLong(), any(Pageable.class));
        verify(itemMapper,          times(1)).toDto(existingItem);
    }

    @Test
    @DisplayName("Get by category — sad path: category not found")
    void getByCategory_CategoryNotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        EmptyResourceException emptyEx =  Assertions.assertThrows(EmptyResourceException.class,
                () -> itemService.getItemsByCategory(1L, 0, 10, "id", "asc"));

        Assertions.assertEquals("No category with id: 1 found", emptyEx.getMessage());
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(itemRepository,     never()).findByCategoryId(anyLong(), any(Pageable.class));
        verify(itemMapper,          never()).toDto(any());
    }

    @Test
    @DisplayName("Get by category — sad path: category exists but has no items")
    void getByCategory_Empty() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(ringsCategory));
        when(itemRepository.findByCategoryId(anyLong(), any(Pageable.class))).thenReturn(TestDataFactory.emptyPage());

        APIResponse<ItemDTO> res = itemService.getItemsByCategory(1L, 0, 10, "id", "asc");

        Assertions.assertEquals(0, res.getContent().size());
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(itemRepository,     times(1)).findByCategoryId(anyLong(), any(Pageable.class));
        verify(itemMapper,          never()).toDto(any());

    }

    // ─── GET BY KEYWORD ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Get by keyword — happy path")
    void getByKeyword_Success() {
        List<Item> itemList = List.of(existingItem);
        Page<Item> itemPage = TestDataFactory.page(itemList);

        when(itemRepository.findByNameContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(itemPage);
        when(itemMapper.toDto(existingItem)).thenReturn(existingItemDTO);

        APIResponse<ItemDTO> result = itemService.getItemsByKeyword("gold", 0, 10, "id", "asc");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(itemList.size(), result.getContent().size());
        Assertions.assertEquals(existingItemDTO.getName(), result.getContent().get(0).getName());
        verify(itemRepository, times(1)).findByNameContainingIgnoreCase(eq("gold"), any(Pageable.class));
        verify(itemMapper,      times(1)).toDto(existingItem);
    }

    @Test
    @DisplayName("Get by keyword — Sad path: no items match keyword( empty APIResponse )")
    void getByKeyword_Empty() {
        when(itemRepository.findByNameContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(TestDataFactory.emptyPage());

        APIResponse<ItemDTO> res = itemService.getItemsByKeyword("cha", 0, 10, "id", "asc");

        Assertions.assertNotNull(res);
        Assertions.assertEquals(0,res.getTotalElements());
        Assertions.assertEquals(0, res.getTotalPages());
        verify(itemMapper,      never()).toDto(any());
    }

    // ─── CREATE ITEM ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Create item — happy path")
    void createItem_Success() {
        ItemDTO   inputDTO    = ItemDTOBuilder.anItemDTO().withName("Silver ring").withId(null).build();
        Item      savedItem   = ItemBuilder.anItem().withName("Silver ring").build();
        ItemDTO   expectedDTO = ItemDTOBuilder.anItemDTO().withName("Silver ring").build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(ringsCategory));
        when(itemRepository.findByName(inputDTO.getName())).thenReturn(Optional.empty());
        when(itemMapper.toEntity(any())).thenReturn(savedItem);
        when(itemRepository.save(any())).thenReturn(savedItem);
        when(itemMapper.toDto(any())).thenReturn(expectedDTO);

        ItemDTO result = itemService.addItem(1L, inputDTO);

        Assertions.assertEquals("Silver ring", result.getName());
        verify(categoryRepository, times(1)).findById(1L);
        verify(itemRepository,     times(1)).findByName("Silver ring");
        verify(itemRepository,     times(1)).save(any());
        verify(itemMapper,          times(1)).toDto(any());
    }

    @Test
    @DisplayName("Create item — sad path: category not found")
    void createItem_CategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        EmptyResourceException ex = Assertions.assertThrows(
                EmptyResourceException.class,
                () -> itemService.addItem(1L, existingItemDTO)
        );

        Assertions.assertEquals("No Category with id: 1 found", ex.getMessage());
        Assertions.assertEquals(1L, ex.getId());
        verify(categoryRepository, times(1)).findById(1L);
        verify(itemRepository,     never()).findByName(anyString());
        verify(itemMapper,          never()).toDto(any());
    }

    @Test
    @DisplayName("Create item — sad path: item name already exists")
    void createItem_DuplicateName() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(ringsCategory));
        when(itemRepository.findByName(anyString())).thenReturn(Optional.of(existingItem));

        DuplicateResourceException ex = Assertions.assertThrows(
                DuplicateResourceException.class,
                () -> itemService.addItem(1L, existingItemDTO)
        );

        Assertions.assertEquals("Item with name: Gold Ring, already exists", ex.getMessage());
        Assertions.assertEquals("name", ex.getResourceField());
        verify(categoryRepository, times(1)).findById(1L);
        verify(itemRepository,     times(1)).findByName(anyString());
        verify(itemMapper,          never()).toDto(any());
    }

    // ─── UPDATE ITEM ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Update item — happy path")
    void updateItem_Success() {
        ItemDTO requestDTO = ItemDTOBuilder.anItemDTO()
                .withName("Silver ring")
                .withId(null)
                .build();
        Item updatedEntity = ItemBuilder.anItem().withName("Silver ring")
                .withId(existingItem.getId())
                .build();

        ItemDTO updatedResult = ItemDTOBuilder.anItemDTO()
                .withName("Silver ring")
                .withId(1L)
                .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(ringsCategory));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(itemRepository.findByName(requestDTO.getName())).thenReturn(Optional.empty());
        when(itemRepository.save(existingItem)).thenReturn(updatedEntity);
        when(itemMapper.toDto(updatedEntity)).thenReturn(updatedResult);

        ItemDTO result = itemService.updateItem(1L, requestDTO, 1L);

        Assertions.assertNull(requestDTO.getProductId());
        Assertions.assertEquals("Silver ring", result.getName());
        Assertions.assertEquals(1L, result.getProductId());
        verify(categoryRepository, times(1)).findById(1L);
        verify(itemRepository,     times(1)).findById(1L);
        verify(itemRepository,     times(1)).findByName("Silver ring");
        verify(itemMapper,          times(1)).toDto(updatedEntity);
        verify(itemMapper,          times(1)).updateFromDto(requestDTO, existingItem);
    }

    @Test
    @DisplayName("Update item — sad path: new name already taken by another item")
    void updateItem_DuplicateName() {
        ItemDTO inputDTO      = ItemDTOBuilder.anItemDTO().withName("Amethyst ring").withId(null).build();
        Item    conflictItem  = ItemBuilder.anItem().withName("Amethyst ring").withId(2L).build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(ringsCategory));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(itemRepository.findByName("Amethyst ring")).thenReturn(Optional.of(conflictItem));

        DuplicateResourceException ex = Assertions.assertThrows(
                DuplicateResourceException.class,
                () -> itemService.updateItem(1L, inputDTO, 1L)
        );

        Assertions.assertEquals("Item with name: Amethyst ring, already exists", ex.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
        verify(itemRepository,     times(1)).findById(1L);
        verify(itemRepository,     times(1)).findByName("Amethyst ring");
        verify(itemMapper,          never()).toDto(any());
    }

    // ─── DELETE ITEM ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Delete item — happy path")
    void deleteItem_Success() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(itemMapper.toDto(existingItem)).thenReturn(existingItemDTO);

        ItemDTO result = itemService.deleteItem(1L);

        Assertions.assertEquals(existingItemDTO.getName(), result.getName());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).delete(existingItem);
        verify(itemMapper,      times(1)).toDto(existingItem);
    }

    @Test
    @DisplayName("Delete item — sad path: item not found")
    void deleteItem_NotFound() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        EmptyResourceException ex = Assertions.assertThrows(
                EmptyResourceException.class,
                () -> itemService.deleteItem(1L)
        );

        Assertions.assertEquals("No Item with id: 1 found", ex.getMessage());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, never()).delete(any(Item.class));
        verify(itemMapper,      never()).toDto(any(Item.class));
    }
}