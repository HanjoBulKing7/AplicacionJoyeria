package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.constants.DefaultValues;
import com.jewelry.managementsystem.payload.APIResponse;
import com.jewelry.managementsystem.payload.ItemDTO;
import com.jewelry.managementsystem.services.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Items", description = "Endpoints for managing jewelry pieces, inventory, and product search")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "Get item by ID", description = "Fetches details of a specific jewelry piece by its unique ID.")
    @GetMapping("/public/items/{itemId}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long itemId) {
        ItemDTO foundItemDTO = itemService.getItem(itemId);
        return new ResponseEntity<>(foundItemDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get all items", description = "Retrieves a paginated list of all jewelry pieces in the catalog.")
    @GetMapping("/public/items")
    public ResponseEntity<APIResponse<ItemDTO>> getItems(
            @RequestParam(name = "pageNumber", defaultValue = DefaultValues.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = DefaultValues.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = DefaultValues.DEFAULT_SORT_FIELD, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = DefaultValues.DEFAULT_SORT_ORDER, required = false) String sortOrder
    ) {
        APIResponse<ItemDTO> itemResponse = itemService.getItems(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get items by category", description = "Filters the product catalog to show items belonging to a specific category.")
    @GetMapping("/public/items/category/{categoryId}")
    public ResponseEntity<APIResponse<ItemDTO>> getItemByCategory(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber", defaultValue = DefaultValues.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = DefaultValues.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = DefaultValues.DEFAULT_SORT_FIELD, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = DefaultValues.DEFAULT_SORT_ORDER, required = false) String sortOrder
    ) {
        APIResponse apiResponse = itemService.getItemsByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Search items by keyword", description = "Searches for jewelry pieces where the name or description matches the provided keyword.")
    @GetMapping("/public/items/search{keyword}")
    public ResponseEntity<APIResponse<ItemDTO>> getItemByKeyword(
            @RequestParam String keyword,
            @RequestParam(name = "pageNumber", defaultValue = DefaultValues.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = DefaultValues.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = DefaultValues.DEFAULT_SORT_FIELD, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = DefaultValues.DEFAULT_SORT_ORDER, required = false) String sortOrder
    ) {
        APIResponse apiResponse = itemService.getItemsByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Create item", description = "Adds a new jewelry piece to a specific category. Admin access required.")
    @PostMapping("/admin/categories/{categoryId}/items")
    public ResponseEntity<ItemDTO> createItem(@Valid @RequestBody ItemDTO itemDTO,
                                              @PathVariable Long categoryId) {
        ItemDTO createdItemDTO = itemService.addItem(categoryId, itemDTO);
        return new ResponseEntity<>(createdItemDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Update item", description = "Updates pricing, stock, or details of an existing item. Admin access required.")
    @PutMapping("/admin/categories/{categoryId}/items/{itemId}")
    public ResponseEntity<ItemDTO> updateItem(
            @Valid @RequestBody ItemDTO itemDTO,
            @PathVariable Long itemId,
            @PathVariable Long categoryId
    ) {
        ItemDTO updatedItemDTO = itemService.updateItem(itemId, itemDTO, categoryId);
        return new ResponseEntity<>(updatedItemDTO, HttpStatus.OK);
    }

    @Operation(summary = "Delete item", description = "Removes a product from the inventory. Admin access required.")
    @DeleteMapping("/admin/items/{itemId}")
    public ResponseEntity<ItemDTO> deleteItem(@PathVariable Long itemId) {
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
