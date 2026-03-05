package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {

    Optional<Item> findByName(@NotBlank  @Size( min = 5, message = "Item name must contain at least 5 characters") String itemName);
}
