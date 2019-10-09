package com.sunasterisk.javaprojecttesting.repository;

import com.sunasterisk.javaprojecttesting.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}