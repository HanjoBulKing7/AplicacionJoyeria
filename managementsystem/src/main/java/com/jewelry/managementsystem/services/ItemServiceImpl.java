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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ItemMapper itemMapper;

    @Override
    public ItemDTO getItem(Long id) {
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new EmptyResourceException( id, "item"));

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

        if (itemsFromDB.isEmpty()) {
            return new APIResponse<ItemDTO>(
                    Collections.emptyList(), // content
                    pageNumber,              // pageNumber
                    pageSize,                // pageSize
                    0L,                      // totalElements
                    0,                       // totalPages
                    true                     // lastPage
            );
        }

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
                .orElseThrow(() -> new EmptyResourceException(categoryId, "category" ));

        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of( pageNumber, pageSize, sortByAndOrder);

        Page<Item> itemPage = itemRepository.findByCategoryId( categoryId, pageDetails);

        List<Item> itemsFromDB = itemPage.getContent();

        if( itemsFromDB.isEmpty() )
            return new APIResponse<>(Collections.emptyList(),
                    pageNumber,
                    pageSize,
                    0L,
                    0,
                    true);

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

        if( itemsFromDB.isEmpty() )
            return new APIResponse<>(Collections.emptyList(),
                    pageNumber,
                    pageSize,
                    0L,
                    0,
                    true);

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
                .orElseThrow(() -> new EmptyResourceException(categoryId, "Category"));

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
                .orElseThrow(() -> new EmptyResourceException(categoryId, "Category"));

        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new EmptyResourceException(itemId, "Item"));

        if (!existingItem.getName().equals(itemDTO.getName())) {
            itemRepository.findByName(itemDTO.getName()).ifPresent(i -> {
                throw new DuplicateResourceException("Item", "name", itemDTO.getName());
            });
        }

        itemMapper.updateFromDto(itemDTO, existingItem);
        existingItem.setCategory(existingCategory);

        Item savedItem = itemRepository.save(existingItem);
        return itemMapper.toDto(savedItem);
    }

    @Override
    public ItemDTO deleteItem(Long itemId) {
        Item toDelete = itemRepository.findById(itemId)
                .orElseThrow(() -> new EmptyResourceException(itemId, "Item"));

        itemRepository.delete(toDelete);

        return itemMapper.toDto(toDelete);
    }

    @Override
    public ItemDTO updateItemImage(Long itemId, MultipartFile image) throws IOException {

        // Get the product from
        Item itemToUpdate = itemRepository.findById(itemId)
                .orElseThrow(() -> new EmptyResourceException(itemId, "Item"));
        //Upload iamge to server
        //Get  the file name of uploaded image
        String path = System.getProperty("user.dir") + File.separator + "images" + File.separator;/// Create path to save all images and getting current directory
        String fileName = uploadImage(path, image);
        //Updating the new file name to the item
        itemToUpdate.setImage(fileName);
        /// Save  updated item
        Item updatedItem = itemRepository.save(itemToUpdate);
        // Return DTO after mapping modified item
        return  itemMapper.toDto(updatedItem);
    }

    public String uploadImage(String path, MultipartFile image) throws IOException {
        ///  File name of current
        String originalFileName = image.getOriginalFilename();
        ///  Generate unique file name
        String randomUUID = UUID.randomUUID().toString();
        String fileName = randomUUID.concat(originalFileName.substring(originalFileName.lastIndexOf("."))); /// Get the extension ".jpg", ".jpeg", ".png"
        String filePath = path + File.separator + fileName; /// /image/15245-1231.jpg
        ///  Check if path exists or create if needed
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();
        ///  Upload to server
        Files.copy(image.getInputStream(), Paths.get(filePath));
        ///  Return file
        return fileName;
    }
}
