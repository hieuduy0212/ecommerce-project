package com.hieuduy.admin.controllers;

import com.hieuduy.core.entities.Category;
import com.hieuduy.core.entities.Product;
import com.hieuduy.core.services.CategoryService;
import com.hieuduy.core.services.ProductService;
import com.hieuduy.core.utils.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PageController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public PageController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/categories")
    public String goCategoryPage(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Categories manage");
        return "categories";
    }

    @GetMapping("/products")
    public String goProductPage(Model model){
        List<Product> products = productService.findAll();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("title", "Products manage");
        return "products";
    }

    @GetMapping("/edit-product/{productId}")
    public String goEditProductPage(@PathVariable Long productId, Model model){
        Product product = productService.findById(productId);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories).addAttribute("product", product);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("title", "Edit product");
        return "edit-product";
    }
}
