package com.hieuduy.admin.controllers;

import com.hieuduy.core.entities.ImageModel;
import com.hieuduy.core.entities.Product;
import com.hieuduy.core.services.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product addNewProduct(@RequestPart Product product, @RequestPart(name = "imageFile") MultipartFile[] multipartFile) {
        try {
            List<ImageModel> lst = uploadImage(multipartFile);
            product.setImages(lst);
            return productService.create(product);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private List<ImageModel> uploadImage(MultipartFile[] files) throws IOException {
        if (files != null && files.length != 0) {
            List<ImageModel> lst = new ArrayList<>();
            for (MultipartFile file : files) {
                lst.add(ImageModel.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .picByte(file.getBytes())
                        .build());
            }
            return lst;
        }
        return null;
    }

    @DeleteMapping("/product/{productId}")
    public void changeProductStatus(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        if (product.isActivated())
            productService.deleteById(productId);
        else
            productService.enableById(productId);
    }

    @GetMapping("/product/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productService.findById(productId);
    }

    @DeleteMapping("/product/{imgId}/{productId}")
    public void deleteImageOfProduct(@PathVariable Long imgId, @PathVariable Long productId) {
        productService.deleteImgOfProduct(imgId, productId);
    }

    @PutMapping("/product")
    public Product doUpdateProduct(@RequestPart Product product, @RequestPart(required = false) MultipartFile[] imageFile) {
        System.out.println(product.getCategory());
        try {
            return productService.update(product, uploadImage(imageFile));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
