package com.hieuduy.customer.controller;

import com.hieuduy.core.entities.Category;
import com.hieuduy.core.entities.Product;
import com.hieuduy.core.services.CategoryService;
import com.hieuduy.core.services.ProductService;
import com.hieuduy.core.utils.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class PageController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public PageController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = {"/", "/index", "/home"})
    public String goHome(Model model) {

        List<Product> products = productService.findAll();
        List<Category> categories = categoryService.findAll();

        model.addAttribute("title", "Home page");
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("imgUtil", new ImageUtil());

        return "index";
    }
}
