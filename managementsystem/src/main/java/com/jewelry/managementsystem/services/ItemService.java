package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.payload.APIResponse;
import com.jewelry.managementsystem.payload.ItemDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ItemService {

    ItemDTO getItem(Long id);
    APIResponse<ItemDTO> getItems(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    APIResponse<ItemDTO> getItemsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    APIResponse<ItemDTO> getItemsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    ItemDTO addItem(Long categoryId, ItemDTO itemDTO);
    ItemDTO updateItem(Long itemId,ItemDTO itemDTO, Long categoryId);
    ItemDTO deleteItem(Long itemId);

    ItemDTO updateItemImage(Long itemId, MultipartFile image) throws IOException;
}