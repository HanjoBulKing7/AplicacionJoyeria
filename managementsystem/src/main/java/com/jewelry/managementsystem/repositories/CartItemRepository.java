package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.originalItem.id = :itemId")
    CartItem findByCartIdAndItemId(@Param("cartId") Long cartId, @Param("itemId") Long itemId);
}
