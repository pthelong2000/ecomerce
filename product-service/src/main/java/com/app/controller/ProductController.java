package com.app.controller;

import com.app.entity.Product;
import com.app.model.Message;
import com.app.model.ProductRequest;
import com.app.model.ProductResponse;
import com.app.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;

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

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity) {
        productService.reduceQuantity(productId, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public String get() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                // Do nothing to suppress the default error handling
            }
        });
        ResponseEntity<String> str = restTemplate.exchange(
                "http://localhost:8082/order",
                HttpMethod.GET, // hoặc HttpMethod.POST tùy thuộc vào loại request của bạn
                null,
                String.class,
                new ResponseExtractor<String>() {
                    @Override
                    public String extractData(ClientHttpResponse response){
                        // Đọc response body và trả về giá trị từ trường "message"
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            Map<String, String> errorBody = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, String>>() {});
                            String errorMessage = errorBody.get("message");
                            System.out.println("Error Message: " + errorMessage);
                            return errorMessage;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    return "b";}
                });
        return str.getBody();


    }
}

