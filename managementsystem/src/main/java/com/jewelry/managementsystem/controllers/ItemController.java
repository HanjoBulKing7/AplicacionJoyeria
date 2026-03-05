package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.constants.DefaultValues;
import com.jewelry.managementsystem.payload.ItemDTO;
import com.jewelry.managementsystem.payload.ItemResponse;
import com.jewelry.managementsystem.services.ItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
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
    public ResponseEntity<ItemResponse> getItems(
            @RequestParam ( name = "pageNumber" , defaultValue = DefaultValues.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam ( name = "pageSize" , defaultValue = DefaultValues.DEFAULT_PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam ( name = "sortBy", defaultValue = DefaultValues.DEFAULT_SORT_FIELD , required = false )  String sortBy,
            @RequestParam ( name = "sortOrder", defaultValue = DefaultValues.DEFAULT_SORT_ORDER, required = false ) String sortOrder
    ){
        ItemResponse itemResponse = itemService.getItems( pageNumber , pageSize , sortBy , sortOrder);

        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    @PostMapping ( "/admin/items" )
    public ResponseEntity<ItemDTO> createItem(@Valid @RequestBody ItemDTO itemDTO){

        ItemDTO createdItemDTO = itemService.addItem(itemDTO);

        return new ResponseEntity<>( createdItemDTO, HttpStatus.CREATED);
    }

    @PutMapping ( "/admin/items/{itemId}" )
    public ResponseEntity<ItemDTO> updateItem(
            @Valid @RequestBody ItemDTO itemDTO,
            @PathVariable Long itemId
    ){
        ItemDTO updatedItemDTO = itemService.updateItem(itemId, itemDTO);
        return new ResponseEntity<>(updatedItemDTO, HttpStatus.OK);

    }

    @DeleteMapping ( "/admin/items/{itemId}" )
    public ResponseEntity<ItemDTO> deleteItem(@PathVariable Long itemId){
        ItemDTO deletedItemDTO = itemService.deleteItem(itemId);

        return new ResponseEntity<>(deletedItemDTO, HttpStatus.OK);
    }
}
