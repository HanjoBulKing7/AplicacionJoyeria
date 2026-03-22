package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.models.User;
import com.jewelry.managementsystem.payload.AddressDTO;
import com.jewelry.managementsystem.services.AddressService;
import com.jewelry.managementsystem.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AuthUtil authUtil;

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/admin/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        List<AddressDTO> addresses = addressService.getAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddresses() {
        List<AddressDTO> addresses = addressService.getUserAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO) {
        AddressDTO created = addressService.createAddress(addressDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId,
                                                    @RequestBody AddressDTO addressDTO) {
        AddressDTO updated = addressService.updateAddress(addressId, addressDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
        String result = addressService.deleteAddress(addressId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}