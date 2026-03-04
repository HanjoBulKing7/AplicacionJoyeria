package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.exceptions.APIExceptions;
import com.jewelry.managementsystem.exceptions.EmptyResourceException;
import com.jewelry.managementsystem.models.Item;
import com.jewelry.managementsystem.payload.ItemDTO;
import com.jewelry.managementsystem.payload.ItemResponse;
import com.jewelry.managementsystem.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {


    private final ItemRepository itemRepository;

    private final ModelMapper modelMapper = new  ModelMapper();

    @Override
    public ItemDTO getItem(String id) {
        return null;
    }

    @Override
    public ItemResponse getItems(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        /// Checking the order direction to create the object
        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        ///  Setting up the object of page to limnit the content of the response
        Pageable pageDetails = PageRequest.of( pageNumber, pageSize, sortByAndOrder);
        ///  Querying the database based on the created page
        Page<Item> itemPage = itemRepository.findAll(pageDetails);

        ///  Get content from the page to create an ItemResponse
        List<Item> itemsFromDB = itemPage.getContent();

        if( itemsFromDB.isEmpty() ) throw new EmptyResourceException();

        List<ItemDTO>  itemsDTO = itemsFromDB.stream()
                .map( item -> modelMapper.map(item, ItemDTO.class))
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
        return null;
    }

    @Override
    public ItemDTO updateItem(Long itemId, ItemDTO itemDTO) {
        return null;
    }

    @Override
    public ItemDTO deleteItem(Long itemId) {
        return null;
    }
}
