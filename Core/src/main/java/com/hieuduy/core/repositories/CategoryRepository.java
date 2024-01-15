package com.hieuduy.core.repositories;

import com.hieuduy.core.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameLike(String name);
    Optional<Category> findByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
