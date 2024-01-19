package com.hieuduy.core.repositories;

import com.hieuduy.core.entities.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Modifying
    @Transactional
    @Query("update CartItem set quantity=?2 where id=?1")
    public void update(Long cartItemId, int quantity);

    public Optional<CartItem> findCartItemById(Long cartItemId);
}
