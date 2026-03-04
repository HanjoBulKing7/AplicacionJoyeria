package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {


}
