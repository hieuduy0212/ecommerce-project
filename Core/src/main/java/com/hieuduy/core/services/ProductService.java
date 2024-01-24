package com.hieuduy.core.services;

import com.hieuduy.core.entities.ImageModel;
import com.hieuduy.core.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);
    Product save(Product productDto);
    Product update(Product productDto, List<ImageModel> imageFile);
    void deleteById(Long id);
    void enableById(Long id);
    Product create(Product product);

    void deleteImgOfProduct(Long imgId, Long productId);

    List<Product> findAllByCategoryId(Long categoryId);
}
