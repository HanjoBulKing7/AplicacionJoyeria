package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.constants.DefaultValues;
import com.jewelry.managementsystem.payload.APIResponse;
import com.jewelry.managementsystem.payload.ItemDTO;
import com.jewelry.managementsystem.services.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping  ( "/api" )
public class ItemController {

    private final ItemService itemService;

    @GetMapping ( "/public/items/{itemId}" )
    public ResponseEntity<ItemDTO> getItemById(
            @PathVariable Long itemId
    ){
        ItemDTO foundItemDTO = itemService.getItem(itemId);

        return new ResponseEntity<>(foundItemDTO, HttpStatus.OK);

    }

    @GetMapping( "/public/items" )
    public ResponseEntity<APIResponse<ItemDTO>> getItems(
            @RequestParam ( name = "pageNumber" , defaultValue = DefaultValues.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam ( name = "pageSize" , defaultValue = DefaultValues.DEFAULT_PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam ( name = "sortBy", defaultValue = DefaultValues.DEFAULT_SORT_FIELD , required = false )  String sortBy,
            @RequestParam ( name = "sortOrder", defaultValue = DefaultValues.DEFAULT_SORT_ORDER, required = false ) String sortOrder
    ){
        APIResponse<ItemDTO> itemResponse = itemService.getItems( ((pageNumber <= 0) ? 0 : pageNumber - 1), pageSize , sortBy , sortOrder);

        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    @GetMapping ( "/public/items/category/{categoryId}")
    public ResponseEntity<APIResponse<ItemDTO>> getItemByCategory(
            @PathVariable Long categoryId ,
            @RequestParam ( name = "pageNumber" , defaultValue = DefaultValues.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam ( name = "pageSize" , defaultValue = DefaultValues.DEFAULT_PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam ( name = "sortBy", defaultValue = DefaultValues.DEFAULT_SORT_FIELD , required = false )  String sortBy,
            @RequestParam ( name = "sortOrder", defaultValue = DefaultValues.DEFAULT_SORT_ORDER, required = false ) String sortOrder
    ){

        APIResponse apiResponse = itemService.getItemsByCategory(categoryId, ((pageNumber <= 0) ? 0 : pageNumber - 1), pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping ("/public/items/search")
    public ResponseEntity<APIResponse<ItemDTO>> getItemByKeyword(
            @RequestParam (name = "keyword", required = false) String keyword,
            @RequestParam ( name = "pageNumber" , defaultValue = DefaultValues.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam ( name = "pageSize" , defaultValue = DefaultValues.DEFAULT_PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam ( name = "sortBy", defaultValue = DefaultValues.DEFAULT_SORT_FIELD , required = false )  String sortBy,
            @RequestParam ( name = "sortOrder", defaultValue = DefaultValues.DEFAULT_SORT_ORDER, required = false ) String sortOrder
    ){
        APIResponse apiResponse = itemService.getItemsByKeyword(keyword, ((pageNumber <= 0) ? 0 : pageNumber - 1), pageSize, sortBy, sortOrder);

        return new ResponseEntity<>( apiResponse, HttpStatus.OK);
    }


    @PostMapping("/admin/categories/{categoryId}/items")
    public ResponseEntity<ItemDTO> createItem(@Valid @RequestBody ItemDTO itemDTO,
                                              @PathVariable Long categoryId){

        ItemDTO createdItemDTO = itemService.addItem(categoryId, itemDTO);

        return new ResponseEntity<>( createdItemDTO, HttpStatus.CREATED);
    }

    @PutMapping("/admin/categories/{categoryId}/items/{itemId}")
    public ResponseEntity<ItemDTO> updateItem(
            @Valid @RequestBody ItemDTO itemDTO,
            @PathVariable Long itemId,
            @PathVariable Long categoryId
    ){
        ItemDTO updatedItemDTO = itemService.updateItem(itemId, itemDTO, categoryId);
        return new ResponseEntity<>(updatedItemDTO, HttpStatus.OK);

    }

    @DeleteMapping ( "/admin/items/{itemId}" )
    public ResponseEntity<ItemDTO> deleteItem(@PathVariable Long itemId){
        ItemDTO deletedItemDTO = itemService.deleteItem(itemId);

        return new ResponseEntity<>(deletedItemDTO, HttpStatus.OK);
    }

    @PutMapping("/items/{itemId}/image")
    public ResponseEntity<ItemDTO> updateProductImage(@PathVariable Long itemId,
                                                      @RequestParam("Image") MultipartFile image) throws IOException {
        ItemDTO updatedImageItem = itemService.updateItemImage(itemId, image);
        return new ResponseEntity<>(updatedImageItem, HttpStatus.OK);
    }
}
