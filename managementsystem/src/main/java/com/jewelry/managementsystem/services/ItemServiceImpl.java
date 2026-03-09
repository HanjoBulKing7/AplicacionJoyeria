package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.exceptions.DuplicateResourceException;
import com.jewelry.managementsystem.exceptions.EmptyResourceException;
import com.jewelry.managementsystem.exceptions.ResourceNotFound;
import com.jewelry.managementsystem.mapper.ItemMapper;
import com.jewelry.managementsystem.models.Category;
import com.jewelry.managementsystem.models.Item;
import com.jewelry.managementsystem.payload.APIResponse;
import com.jewelry.managementsystem.payload.ItemDTO;
import com.jewelry.managementsystem.repositories.CategoryRepository;
import com.jewelry.managementsystem.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private final ItemMapper itemMapper;

    @Override
    public ItemDTO getItem(Long id) {

        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Item", id));

        return itemMapper.toDto(existingItem);
    }

    @Override
    public APIResponse getItems(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        /// Checking the order direction to create the object
        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        ///  Setting up the object of page to limnit the content of the response
        Pageable pageDetails = PageRequest.of( pageNumber, pageSize, sortByAndOrder);
        ///  Querying the database based on the created
        ///log.info("page details"+ pageDetails);
        Page<Item> itemPage = itemRepository.findAll(pageDetails);

        ///log.info("Elements from repository: "+ itemRepository.findAll());
        ///log.info("Elements found #"+itemPage.getTotalElements()+" following: "+itemPage.getContent());
        ///  Get content from the page to create an ItemResponse
        List<Item> itemsFromDB = itemPage.getContent();

        if( itemsFromDB.isEmpty() ) throw new EmptyResourceException("items");

        List<ItemDTO>  itemsDTO = itemsFromDB.stream()
                .map( itemMapper::toDto)
                .toList();

        APIResponse<ItemDTO> itemResponse = new APIResponse<>();
        ///  Create the response with the corresponding values
        itemResponse.setContent(itemsDTO);
        /// Assign the page values
        itemResponse.setPageNumber( itemPage.getNumber());
        itemResponse.setPageSize(itemPage.getSize());
        itemResponse.setTotalElements(itemPage.getTotalElements());
        itemResponse.setTotalPages(itemPage.getTotalPages());
        itemResponse.setLastPage(itemPage.isLast());

        return itemResponse;

    }

    @Override
    public APIResponse<ItemDTO> getItemsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("category", categoryId));

        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of( pageNumber, pageSize, sortByAndOrder);

        Page<Item> itemPage = itemRepository.findByCategoryId( categoryId, pageDetails);

        List<Item> itemsFromDB = itemPage.getContent();

        if( itemsFromDB.isEmpty() ) throw new EmptyResourceException("items");

        List<ItemDTO> resultPageDTO = itemsFromDB.stream()
                .map(itemMapper::toDto).toList();

        APIResponse<ItemDTO> itemResponse = new APIResponse<>();
        itemResponse.setContent(resultPageDTO);
        itemResponse.setPageNumber( itemPage.getNumber());
        itemResponse.setPageSize(itemPage.getSize());
        itemResponse.setTotalElements(itemPage.getTotalElements());
        itemResponse.setTotalPages(itemPage.getTotalPages());
        itemResponse.setLastPage(itemPage.isLast());

        return itemResponse;
    }

    @Override
    public APIResponse<ItemDTO> getItemsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of( pageNumber, pageSize, sortByAndOrder);

        Page<Item> byKeywordPage = itemRepository.findByNameContainingIgnoreCase( keyword, pageDetails );

        List<Item> itemsFromDB = byKeywordPage.getContent();

        if( itemsFromDB.isEmpty() ) throw new EmptyResourceException("items");

        APIResponse<ItemDTO> itemResponse = new APIResponse<>();

        List<ItemDTO> itemsDTO = itemsFromDB.stream()
                .map(itemMapper::toDto)
                .toList();
        itemResponse.setContent(itemsDTO);
        itemResponse.setPageNumber(byKeywordPage.getNumber());
        itemResponse.setPageSize(byKeywordPage.getSize());
        itemResponse.setTotalElements(byKeywordPage.getTotalElements());
        itemResponse.setTotalPages(byKeywordPage.getTotalPages());
        itemResponse.setLastPage(byKeywordPage.isLast());

        return  itemResponse;
    }

    @Override
    public ItemDTO addItem(Long categoryId, ItemDTO itemDTO) {

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Category", categoryId));


        itemRepository.findByName(itemDTO.getName())
                .ifPresent(item -> {
                    throw new DuplicateResourceException("Item", "name", itemDTO.getName());
                });

        Item toSave = itemMapper.toEntity(itemDTO);
        toSave.setCategory(existingCategory);
        ItemDTO savedItemDTO = itemMapper.toDto(itemRepository.save(toSave));

        log.info(savedItemDTO.toString());

        return  savedItemDTO;
    }


    @Override
    public ItemDTO updateItem(Long itemId, ItemDTO itemDTO, Long categoryId) {

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Category", categoryId));

        return itemRepository.findById(itemId)
                .map(
                        item -> {
                            itemMapper.updateFromDto(itemDTO, item);
                            item.setCategory(existingCategory);
                            itemRepository.save(item);
                            return itemMapper.toDto(item);
                        })
                .orElseThrow(() -> new ResourceNotFound("Item", itemId));

    }

    @Override
    public ItemDTO deleteItem(Long itemId) {

        Item toDelete = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFound("Item", itemId));

        itemRepository.delete(toDelete);

        return itemMapper.toDto(toDelete);
    }

}
