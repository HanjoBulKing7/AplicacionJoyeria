package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
