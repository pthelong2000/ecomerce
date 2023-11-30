package com.app.service.impl;

import com.app.entity.Product;
import com.app.exception.ProductServiceException;
import com.app.model.ProductRequest;
import com.app.model.ProductResponse;
import com.app.repository.ProductRepository;
import com.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest request) {
        Product product = Product.builder()
                .productName(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        return productRepository.save(product).getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        return productRepository.findById(productId)
                .map(product -> ProductResponse.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .build())
                .orElseThrow(() -> new ProductServiceException(String.format("Product with given id: %s not found", productId)));
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceException(String.format("Product with given id: %s not found", productId)));

        if (product.getQuantity() < quantity) {
            throw new ProductServiceException("Product does not have sufficient quantity");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }
}
