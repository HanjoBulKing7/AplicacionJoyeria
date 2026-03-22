package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.email = :email ")
    Cart findByEmail(@Param("email") String s);
}
