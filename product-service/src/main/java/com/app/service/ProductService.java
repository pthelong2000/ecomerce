package com.app.service;

import com.app.model.ProductRequest;
import com.app.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest request);

    ProductResponse getProductById(long productId);
}
