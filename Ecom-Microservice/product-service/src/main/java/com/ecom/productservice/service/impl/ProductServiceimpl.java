package com.ecom.productservice.service.impl;

import com.ecom.productservice.dto.ProductRequest;
import com.ecom.productservice.dto.ProductResponse;
import com.ecom.productservice.exception.ResourceNotFoundException;
import com.ecom.productservice.model.Product;
import com.ecom.productservice.repository.ProductRepo;
import com.ecom.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceimpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Override
    public Integer creatProduct(ProductRequest productRequest) {
        Product product1 = Product.builder()
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .build();
        Product productCreated = productRepo.save(product1);
        return productCreated.getProductId();
    }

    @Override
    public ProductResponse getProductById(int pid) {
        Product product = productRepo.findByProductId(pid);
        ProductResponse productResponse = ProductResponse.builder()
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
        return productResponse;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
       // ArrayList<ProductResponse> productList = new ArrayList<>();
        return productRepo.findAll()
                .stream().map(product -> ProductResponse.builder()
                        .productName(product.getProductName())
                        .description(product.getDescription())
                        .price(product.getPrice()).build()).collect(Collectors.toList());

    }

    @Override
    public Integer deleteProduct(int pid) {
        if(!productRepo.existsById(pid))
            throw new ResourceNotFoundException("product is not there for id: "+pid);
        productRepo.deleteById(pid);
        return pid;
    }

//    @Override
//    public List<ProductResponse> getAllProducts() {
//        ArrayList<ProductResponse> productList = new ArrayList<>();
//        productRepo.findAll()
//                .stream().map(product -> productList.add(ProductResponse.builder()
//                        .productName(product.getProductName())
//                        .description(product.getDescription())
//                        .price(product.getPrice()).build())
//                ).collect(Collectors.toList());
//        return productList;
//    }
}
