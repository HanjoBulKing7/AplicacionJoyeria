package com.jewelry.managementsystem.mapper;


import com.jewelry.managementsystem.models.Order;
import com.jewelry.managementsystem.models.Address;
import com.jewelry.managementsystem.payload.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel ="spring")
public interface OrderMapper extends GenericMapper<Order, OrderDTO>{

    @Mapping(source = "address", target = "address") // Mapea el objeto Address al String address
    OrderDTO toDto(Order order);

    // MapStruct usará este método automáticamente para convertir Address -> String
    default String mapAddressToString(Address address) {
        if (address == null) return null;
        return String.format("%s, %s, %s, %s",
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode());
    }

}
