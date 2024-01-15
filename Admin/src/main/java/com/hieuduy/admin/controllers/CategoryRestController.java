package com.hieuduy.admin.controllers;

import com.hieuduy.core.entities.Category;
import com.hieuduy.core.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CategoryRestController {
    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public Category doAddNewCategory(@RequestBody Category category) {
        category.setActivated(true);
        Category category1 = categoryService.save(category);
        return category1;
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<Void> doDeleteCategory(@PathVariable(name = "categoryId") Long categoryId) {
        categoryService.deleteById(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/category")
    public Category doUpdateCategory(@RequestBody Category category) {
        Category category1 = categoryService.update(category);
        return category1;
    }
}
