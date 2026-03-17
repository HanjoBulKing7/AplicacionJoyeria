package com.jewelry.managementsystem.service;

import com.jewelry.managementsystem.constants.ItemStatus;
import com.jewelry.managementsystem.exceptions.DuplicateResourceException;
import com.jewelry.managementsystem.exceptions.EmptyResourceException;
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

    Item testItem;
    ItemDTO testItemDTO;
    Category ringsCat;

    @BeforeEach
    public void setUp() {
        ringsCat = new Category(1L, "Rings", Collections.emptyList());
        testItem = new Item(1L, "Gold ring", null, 100.50F, 10, ItemStatus.ACTIVE, ringsCat);
        testItemDTO = new ItemDTO(null, "Gold ring", null, 100.5F, 10, ItemStatus.ACTIVE, ringsCat.getId());
    }

    @Test
    @DisplayName("Get one item")
    void getItem_Success() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));
        when(itemMapper.toDto(testItem)).thenReturn(testItemDTO);

        ItemDTO itemDTO = itemService.getItem(1L);

        Assertions.assertNotNull(itemDTO);
        Assertions.assertEquals(1L, testItem.getId());
        Assertions.assertEquals(testItemDTO.getName(), itemDTO.getName());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemMapper, times(1)).toDto(testItem);
    }

    @Test
    @DisplayName("Get 1 item exception")
    void getAllItemException() {
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        EmptyResourceException emptyEx = Assertions.assertThrows(
                EmptyResourceException.class,
                () -> itemService.getItem(999L)
        );

        Assertions.assertNotNull(emptyEx);
        Assertions.assertEquals("No item with id: 999 found", emptyEx.getMessage());
        Assertions.assertEquals(999L, emptyEx.getId());
        verify(itemMapper, never()).toDto(any());
    }

    @Test
    @DisplayName("Get all items")
    void getAllItems_Success() {
        Pageable pageDetails = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        List itemList = List.of(testItem);
        Page itemnPage = new PageImpl<>(itemList, pageDetails, itemList.size());
        when(itemMapper.toDto(testItem)).thenReturn(testItemDTO);
        when(itemRepository.findAll(any(Pageable.class))).thenReturn(itemnPage);

        APIResponse<ItemDTO> allResponse = itemService.getItems(0, 10, "id", "asc");

        Assertions.assertNotNull(allResponse);
        Assertions.assertEquals(itemList.size(), allResponse.getContent().size());
        verify(itemMapper, times(itemList.size())).toDto(testItem);
        verify(itemRepository, times(1)).findAll(any(Pageable.class));

    }

    @Test
    @DisplayName("Get all items empty exception")
    void getAllItems_Exception() {
        Page emptyPage = new PageImpl<>(Collections.emptyList());
        when(itemRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        EmptyResourceException emptyEx = Assertions.assertThrows(
                EmptyResourceException.class,
                () -> itemService.getItems(0, 10, "id", "asc")
        );

        Assertions.assertEquals("No items found", emptyEx.getMessage());
        verify(itemRepository, times(1)).findAll(any(Pageable.class));
        verify(itemMapper, never()).toDto(any());
    }

    @Test
    @DisplayName("Get by category")
    void getByCategory_Success() {
        Pageable pageDetails = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        List itemList = List.of(testItem);
        Page itemnPage = new PageImpl<>(itemList, pageDetails, itemList.size());
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(ringsCat));
        when(itemMapper.toDto(testItem)).thenReturn(testItemDTO);
        when(itemRepository.findByCategoryId(anyLong(), any(Pageable.class))).thenReturn(itemnPage);

        APIResponse<ItemDTO> itemDTOAPIResponse = itemService.getItemsByCategory(1L, 0, 10, "id", "asc");

        Assertions.assertNotNull(itemDTOAPIResponse);
        Assertions.assertEquals(itemList.size(), itemDTOAPIResponse.getContent().size());
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(itemMapper, times(1)).toDto(testItem);
        verify(itemRepository, times(1)).findByCategoryId(anyLong(), any(Pageable.class));
    }

    @Test
    @DisplayName("Get by category fails ( category not found)")
    void getByCatException() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        EmptyResourceException emtyEX = Assertions.assertThrows(
                EmptyResourceException.class,
                () -> itemService.getItemsByCategory(1L, 0, 10, "id", "asc")
        );

        Assertions.assertEquals("No category with id: 1 found", emtyEX.getMessage());
        verify(itemMapper, never()).toDto(any());
        verify(itemRepository, never()).findByCategoryId(anyLong(), any(Pageable.class));
    }

    @Test
    @DisplayName("Get by category is empty exception")
    void getByCategoryEmpty() {
        Page emptyPage = new PageImpl<>(Collections.emptyList());
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(ringsCat));
        when(itemRepository.findByCategoryId(anyLong(), any(Pageable.class))).thenReturn(emptyPage);

        EmptyResourceException emptyEx = Assertions.assertThrows(
                EmptyResourceException.class,
                () -> itemService.getItemsByCategory(1l, 0, 10, "id", "asc")
        );

        Assertions.assertEquals("No items with category: Rings found", emptyEx.getMessage());

        verify(categoryRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(1)).findByCategoryId(anyLong(), any(Pageable.class));
        verify(itemMapper, never()).toDto(any());

    }

    @Test
    @DisplayName("Get by keyword")
    void getByKeyword_Success() {
        Pageable pageDetails = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        List itemList = List.of(testItem);
        Page itemnPage = new PageImpl<>(itemList, pageDetails, itemList.size());

        when(itemRepository.findByNameContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(itemnPage);
        when(itemMapper.toDto(testItem)).thenReturn(testItemDTO);

        APIResponse<ItemDTO> serviceRes = itemService.getItemsByKeyword("cha", 0, 10, "id", "asc");

        Assertions.assertNotNull(serviceRes);
        Assertions.assertEquals(itemList.size(), serviceRes.getContent().size());
        Assertions.assertEquals(testItemDTO.getName(), serviceRes.getContent().get(0).getName());
        verify(itemMapper, times(1)).toDto(testItem);
        verify(itemRepository, times(1)).findByNameContainingIgnoreCase(eq("cha"), any(Pageable.class));
    }

    @Test
    @DisplayName("Get by keyword empty exception")
    void getByKeyWordEmpty(){
        Page emptyPage = new  PageImpl<>(Collections.emptyList());
        when(itemRepository.findByNameContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(emptyPage);

        EmptyResourceException emptyEx = Assertions.assertThrows(
                    EmptyResourceException.class,
                ()-> itemService.getItemsByKeyword("cha", 0, 10, "id", "asc")
        );

        Assertions.assertEquals("No items with keyword: cha found", emptyEx.getMessage());
        Assertions.assertEquals("cha", emptyEx.getFieldValue());
        verify(itemMapper, never()).toDto(any());
    }

    @Test
    @DisplayName("Create item")
    void createItem_Success(){
        // 1. Crea un DTO de entrada (Silver)
        ItemDTO inputDTO = new ItemDTO(null,"Silver ring", null, 100.5F, 10, ItemStatus.ACTIVE, 1L);

        // 2. Crea la entidad que represente ese mismo anillo (Silver)
        Item savedItem = new Item(1L, "Silver ring", null, 100.5F, 10, ItemStatus.ACTIVE, ringsCat);

        // 3. Crea el DTO de salida esperado (Silver)
        ItemDTO expectedDTO = new ItemDTO(1L, "Silver ring", null, 100.5F, 10, ItemStatus.ACTIVE, 1L);

        // Mocks: Ahora todo es coherente (Entra Silver -> Mapea Silver -> Guarda Silver -> Devuelve Silver)
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(ringsCat));
        when(itemRepository.findByName(inputDTO.getName())).thenReturn(Optional.empty());
        when(itemMapper.toEntity(any())).thenReturn(savedItem);
        when(itemRepository.save(any())).thenReturn(savedItem);
        when(itemMapper.toDto(any())).thenReturn(expectedDTO);

        ItemDTO testResponse = itemService.addItem(1L, inputDTO);

        // Assert
        Assertions.assertEquals("Silver ring", testResponse.getName());
    }

    @Test
    @DisplayName("Create item category exception")
    void createItemDuplicateException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        EmptyResourceException emptyEx = Assertions.assertThrows(
                EmptyResourceException.class,
                ()-> itemService.addItem(1L, testItemDTO)
        );

        Assertions.assertEquals("No Category with id: 1 found", emptyEx.getMessage());
        Assertions.assertEquals(1L, emptyEx.getId());
        verify(itemMapper, never()).toDto(any());
        verify(itemRepository, never()).findByName(anyString());
    }

    @Test
    @DisplayName("Create item duplicate exception")
    void  createItemRepeatedNameException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(ringsCat));
        when(itemRepository.findByName(anyString())).thenReturn(Optional.of(testItem));

        DuplicateResourceException duplicateEx = Assertions.assertThrows(
                DuplicateResourceException.class,
                ()-> itemService.addItem(1L, testItemDTO)
        );

        Assertions.assertEquals("Item with name: Gold ring, already exists", duplicateEx.getMessage());
        Assertions.assertEquals("name", duplicateEx.getResourceField());
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(1)).findByName(anyString());
        verify(itemMapper, never()).toDto(any());
    }

    @Test
    @DisplayName("Update item successfully")
    void updateItem_Success(){
        ItemDTO inputUpdateDTO = new ItemDTO(null,"Silver ring",null,  100.5F, 10, ItemStatus.ACTIVE, 1L);
        ItemDTO resultServiceDTO = new ItemDTO(null,"Silver ring",null,  100.5F, 10, ItemStatus.ACTIVE, 1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(ringsCat));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));
        when(itemRepository.findByName(inputUpdateDTO.getName())).thenReturn(Optional.empty());
        when(itemRepository.save(testItem)).thenReturn(testItem);
        when(itemMapper.toDto(testItem)).thenReturn(resultServiceDTO);

        ItemDTO serviceResult = itemService.updateItem(1L, inputUpdateDTO, 1L);

        Assertions.assertEquals(inputUpdateDTO.getName(), serviceResult.getName());
        Assertions.assertEquals(1L, serviceResult.getCategoryId());
        verify(categoryRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).findByName(inputUpdateDTO.getName());
        verify(itemMapper, times(1)).updateFromDto(inputUpdateDTO, testItem);
    }

    @Test
    @DisplayName("Input item name duplicated")
    void updateItemDuplicateException() {
        ItemDTO inputDTO = new ItemDTO(null, "Amethyst ring", null, 100.5F, 10, ItemStatus.ACTIVE, 1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(ringsCat));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));
        ///  New item that mathces the name and provokes the exception
        Item matchNameItem = new Item();
        matchNameItem.setName(inputDTO.getName());
        when(itemRepository.findByName(inputDTO.getName())).thenReturn(Optional.of(matchNameItem));

        DuplicateResourceException duplicateEx = Assertions.assertThrows(
                DuplicateResourceException.class,
                ()-> itemService.updateItem(1L, inputDTO, 1L)
        );

        Assertions.assertEquals("Item with name: Amethyst ring, already exists", duplicateEx.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).findByName(inputDTO.getName());
        verify(itemMapper, never()).toDto(any());
    }

    @Test
    @DisplayName("Delete item successfully")
    void deleteItem_Success(){
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));
        when(itemMapper.toDto(testItem)).thenReturn(testItemDTO);

        ItemDTO deleted =  itemService.deleteItem(1L);

        Assertions.assertEquals("Gold ring", deleted.getName());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).delete(testItem);
        verify(itemMapper, times(1)).toDto(testItem);
    }

    @Test
    @DisplayName("Delete an item that does not exist")
    void deleteItem_NotFound(){
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        EmptyResourceException emptyEx = Assertions.assertThrows(
                EmptyResourceException.class,
                ()-> itemService.deleteItem(1L)
        );

        Assertions.assertEquals("No Item with id: 1 found", emptyEx.getMessage());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, never()).delete(any(Item.class));
        verify(itemMapper, never()).toDto(any(Item.class));
    }

}
