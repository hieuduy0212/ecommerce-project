package com.hieuduy.core.services;

import com.hieuduy.core.entities.ImageModel;
import com.hieuduy.core.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;
import java.util.Set;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);
    Product save(Product productDto);
    Product update(Product productDto, List<ImageModel> imageFile);
    void deleteById(Long id);
    void enableById(Long id);
    Product create(Product product);

    void deleteImgOfProduct(Long imgId, Long productId);
}
