package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.models.User;
import com.jewelry.managementsystem.payload.AddressDTO;
import com.jewelry.managementsystem.services.AddressService;
import com.jewelry.managementsystem.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Address", description = "Endpoints for managing user and shipping addresses")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AuthUtil authUtil;

    @Operation(summary = "Get address by ID", description = "Fetch a specific address using its unique identifier.")
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get all addresses (Admin)", description = "Retrieve a full list of all addresses in the system. Restricted to Admin.")
    @GetMapping("/admin/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        List<AddressDTO> addresses = addressService.getAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @Operation(summary = "Get current user addresses", description = "Retrieve all addresses associated with the currently authenticated user.")
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddresses() {
        List<AddressDTO> addresses = addressService.getUserAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @Operation(summary = "Create new address", description = "Register a new address for the authenticated user.")
    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO) {
        AddressDTO created = addressService.createAddress(addressDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Update address", description = "Modify an existing address based on the provided ID and data.")
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId,
                                                    @RequestBody AddressDTO addressDTO) {
        AddressDTO updated = addressService.updateAddress(addressId, addressDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete address", description = "Permanently remove an address from the system.")
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
        String result = addressService.deleteAddress(addressId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}