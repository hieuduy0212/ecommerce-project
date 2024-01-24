package com.hieuduy.core.repositories;

import com.hieuduy.core.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    List<Product> findAllByCategoryId(Long categoryId);

}
