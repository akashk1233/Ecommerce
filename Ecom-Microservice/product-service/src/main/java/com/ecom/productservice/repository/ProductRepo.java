package com.ecom.productservice.repository;

import com.ecom.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product findByProductId(int pid);
}
