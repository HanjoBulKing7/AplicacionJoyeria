package com.jewelry.managementsystem.mapper;

import com.jewelry.managementsystem.models.Address;
import com.jewelry.managementsystem.payload.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring")
public interface AddressMapper extends GenericMapper<Address, AddressDTO> {

    @Override
    @Mapping(target = "addressId", ignore = true)
    void updateFromDto(AddressDTO dto, @MappingTarget Address address);

}
