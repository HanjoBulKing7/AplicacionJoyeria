package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.payload.ItemDTO;
import com.jewelry.managementsystem.payload.ItemResponse;

import java.util.List;

public interface ItemService {

    ItemDTO getItem(String id);
    ItemResponse getItems(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    ItemDTO addItem(ItemDTO itemDTO);
    ItemDTO updateItem(Long itemId,ItemDTO itemDTO);
    ItemDTO deleteItem(Long itemId);

}