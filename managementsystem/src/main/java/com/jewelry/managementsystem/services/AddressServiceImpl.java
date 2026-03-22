package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.exceptions.EmptyResourceException;
import com.jewelry.managementsystem.mapper.AddressMapper;
import com.jewelry.managementsystem.models.Address;
import com.jewelry.managementsystem.models.User;
import com.jewelry.managementsystem.payload.AddressDTO;
import com.jewelry.managementsystem.repositories.AddressRepository;
import com.jewelry.managementsystem.repositories.UserRepository;
import com.jewelry.managementsystem.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;
    private final AuthUtil authUtil;

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EmptyResourceException(addressId, "address"));
        return addressMapper.toDto(address);
    }

    @Override
    public List<AddressDTO> getAddresses() {
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty())
            throw new EmptyResourceException("addresses");
        return addressMapper.toDtosList(addresses);
    }

    @Override
    public List<AddressDTO> getUserAddresses() {
        List<Address> userAddresses = authUtil.loggedInUser().getAddresses();
        if (userAddresses.isEmpty())
            throw new EmptyResourceException("addresses");
        return addressMapper.toDtosList(userAddresses);
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        User currentUser = authUtil.loggedInUser();
        Address address = addressMapper.toEntity(addressDTO);
        address.setUser(currentUser);
        currentUser.getAddresses().add(address);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.toDto(savedAddress);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new EmptyResourceException(addressId, "address"));

        addressMapper.updateFromDto(addressDTO, existingAddress);

        Address updatedAddress = addressRepository.save(existingAddress);

        User currentUser = existingAddress.getUser();
        currentUser.getAddresses().removeIf(a -> a.getAddressId().equals(addressId));
        currentUser.getAddresses().add(updatedAddress);
        userRepository.save(currentUser);

        return addressMapper.toDto(updatedAddress);
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EmptyResourceException(addressId, "address"));

        User user = address.getUser();
        user.getAddresses().removeIf(a -> a.getAddressId().equals(addressId));
        userRepository.save(user);
        addressRepository.delete(address);

        return "Address with id: " + addressId + " deleted successfully";
    }
}