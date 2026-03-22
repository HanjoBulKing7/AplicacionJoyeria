package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO getAddressById(Long addressId);
    List<AddressDTO> getAddresses();
    List<AddressDTO> getUserAddresses();
    AddressDTO createAddress(AddressDTO addressDTO);
    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);
    String deleteAddress(Long addressId);
}