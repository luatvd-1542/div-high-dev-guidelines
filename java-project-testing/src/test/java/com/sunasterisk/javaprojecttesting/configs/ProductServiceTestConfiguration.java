package com.sunasterisk.javaprojecttesting.configs;

import com.sunasterisk.javaprojecttesting.service.ProductService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class ProductServiceTestConfiguration {
    @Bean
    @Primary
    public ProductService productService() {
        return Mockito.mock(ProductService.class);
    }
}