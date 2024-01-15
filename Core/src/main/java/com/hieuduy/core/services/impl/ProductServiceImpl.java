package com.hieuduy.core.services.impl;

import com.hieuduy.core.entities.ImageModel;
import com.hieuduy.core.entities.Product;
import com.hieuduy.core.exception.DuplicateName;
import com.hieuduy.core.repositories.ProductRepository;
import com.hieuduy.core.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public Product save(Product product) {
        Optional<Product> productOp = productRepository.findByName(product.getName());
        if (productOp.isPresent()) {
            throw new DuplicateName("Duplicate name");
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Product newProduct, List<ImageModel> imageFile) {
        if (productRepository.existsByNameAndIdNot(newProduct.getName(), newProduct.getId())) {
            throw new DuplicateName("Duplicate name");
        }
        Product product = productRepository.findById(newProduct.getId()).get();
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setCategory(newProduct.getCategory());
        product.setPrice(newProduct.getPrice());
        product.setSale(newProduct.getSale());
        product.setCurrentQuantity(newProduct.getCurrentQuantity());

        if (imageFile != null) {
            List<ImageModel> curProductImageModel = product.getImages();
            curProductImageModel.addAll(imageFile);
            product.setImages(curProductImageModel);
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Product> productOp = productRepository.findById(id);
        if (productOp.isPresent()) {
            Product product = productOp.get();
            product.setActivated(false);
            productRepository.save(product);
        }
    }

    @Override
    public void enableById(Long id) {
        Optional<Product> productOp = productRepository.findById(id);
        if (productOp.isPresent()) {
            Product product = productOp.get();
            product.setActivated(true);
            productRepository.save(product);
        }
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteImgOfProduct(Long imgId, Long productId) {
        Optional<Product> productOp = productRepository.findById(productId);
        if (productOp.isPresent()) {
            Product product = productOp.get();
            for (ImageModel im : product.getImages()) {
                if (Objects.equals(im.getId(), imgId)) {
                    product.getImages().remove(im);
                    break;
                }
            }
            productRepository.save(product);
        }


    }
}
