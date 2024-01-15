package com.hieuduy.core.services.impl;

import com.hieuduy.core.entities.Category;
import com.hieuduy.core.exception.DuplicateName;
import com.hieuduy.core.repositories.CategoryRepository;
import com.hieuduy.core.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServerImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> findAllByName(String name) {
        return categoryRepository.findByNameLike(name);
    }

    @Override
    public Category save(Category category) {
        Optional<Category> category1 = categoryRepository.findByName(category.getName());
        if (category1.isPresent()) {
            throw new DuplicateName("Duplicate name");
        } else {
            return categoryRepository.save(category);
        }
    }

    @Override
    public Category update(Category category) {
        if(categoryRepository.existsByNameAndIdNot(category.getName(), category.getId())){
            throw new DuplicateName("Duplicate name");
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Category> op = categoryRepository.findById(id);
        if (op.isPresent()) {
            Category category = op.get();
            category.setActivated(!category.isActivated());
            categoryRepository.save(category);
        }
    }

}
