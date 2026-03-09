package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByName(String name);
    boolean existsByNameAndIdNot(String name,Long id);

}
