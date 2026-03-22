package com.jewelry.managementsystem.mapper;

import com.jewelry.managementsystem.models.Address;
import com.jewelry.managementsystem.payload.AddressDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface AddressMapper extends GenericMapper<Address, AddressDTO> {
}
