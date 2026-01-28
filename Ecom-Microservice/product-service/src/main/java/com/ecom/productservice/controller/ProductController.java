package com.ecom.productservice.controller;

import com.ecom.productservice.dto.ProductRequest;
import com.ecom.productservice.dto.ProductResponse;
import com.ecom.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("")
    public ResponseEntity<Integer> createProduct(@RequestBody ProductRequest productRequest)
    {
        return new ResponseEntity<>(productService.creatProduct(productRequest),HttpStatus.CREATED);
    }

    @GetMapping("/getProduct/{pid}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int pid)
    {
        return new ResponseEntity<>(productService.getProductById(pid),HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponse>> getAllProducts()
    {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Integer> deleteProduct(@RequestParam int pid)
    {
        return new ResponseEntity<>(productService.deleteProduct(pid),HttpStatus.OK);
    }
}
