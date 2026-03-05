package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.exceptions.APIExceptions;
import com.jewelry.managementsystem.exceptions.DuplicateResourceException;
import com.jewelry.managementsystem.exceptions.EmptyResourceException;
import com.jewelry.managementsystem.exceptions.ResourceNotFound;
import com.jewelry.managementsystem.mapper.ItemMapper;
import com.jewelry.managementsystem.models.Item;
import com.jewelry.managementsystem.payload.ItemDTO;
import com.jewelry.managementsystem.payload.ItemResponse;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {


    private final ItemRepository itemRepository;

    @Autowired
    private final ItemMapper itemMapper;

    @Override
    public ItemDTO getItem(Long id) {

        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Item", id));

        return itemMapper.toDto(existingItem);
    }

    @Override
    public ItemResponse getItems(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

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

        ItemResponse itemResponse = new ItemResponse();
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
    public ItemDTO addItem(ItemDTO itemDTO) {

        itemRepository.findByName(itemDTO.getName())
                .ifPresent(item -> {throw new DuplicateResourceException("Item", "name", itemDTO.getName());
                            });

        return  itemMapper.toDto(itemRepository.save(itemMapper.toEntity(itemDTO)));
    }


    @Override
    public ItemDTO updateItem(Long itemId, ItemDTO itemDTO) {

        return itemRepository.findById(itemId)
                .map(
                        item -> {
                            itemMapper.updateFromDto(itemDTO, item);
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
