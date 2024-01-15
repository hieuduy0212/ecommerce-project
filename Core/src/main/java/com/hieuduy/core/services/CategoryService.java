package com.hieuduy.core.services;

import com.hieuduy.core.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(Long id);

    List<Category> findAllByName(String name);

    Category save(Category category);

    Category update(Category category);

    void deleteById(Long id);

}
