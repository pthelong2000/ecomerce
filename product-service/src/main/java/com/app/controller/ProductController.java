package com.app.controller;

import com.app.entity.Product;
import com.app.model.ProductRequest;
import com.app.model.ProductResponse;
import com.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest request) {
        long productId = productService.addProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }
}
