package com.jewelry.managementsystem.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDTO {

    private Long addressId;
    @NotBlank ( message = "Street is mandatory")
    @Size( min = 4, max = 70 )
    private String street;
    @NotBlank ( message = "Neighborhood is mandatory")
    @Size( min = 4, max = 70 )
    private String neighborhood;
    @NotBlank ( message = "City is mandatory")
    @Size( min = 3, max = 25 )
    private String city;
    @NotBlank ( message = "Neighborhood is mandatory")
    @Size( min = 4, max = 25 )
    private String state;
    @NotBlank(message="Country is mandatory")
    @Size( min = 2, max = 20 )
    private String country;
    @NotBlank(message="Zip code  is mandatory")
    @Size(min = 3, max = 10)
    private String zipCode;

}
