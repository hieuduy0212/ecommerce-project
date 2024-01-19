package com.hieuduy.core.repositories;

import com.hieuduy.core.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("select sc from ShoppingCart sc where sc.customer.id=?1")
    Optional<ShoppingCart> findByCustomerId(Long customerId);
    ShoppingCart save(ShoppingCart cart);

    Optional<ShoppingCart> findByCustomerUsername(String username);
}
