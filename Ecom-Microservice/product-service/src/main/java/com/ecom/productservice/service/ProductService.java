package com.ecom.productservice.service;

import com.ecom.productservice.dto.ProductRequest;
import com.ecom.productservice.dto.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    public Integer creatProduct(ProductRequest productRequest);
    public ProductResponse getProductById(int pid);
    public List<ProductResponse> getAllProducts();
    public Integer deleteProduct(int pid);
}
