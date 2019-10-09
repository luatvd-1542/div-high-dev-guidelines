package com.sunasterisk.javaprojecttesting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    ProductService productService;

    public OrderService(ProductService productService) {
        this.productService = productService;
    }
    public String getProductName() {
        return productService.findById(3L).get().getName();
    }
}