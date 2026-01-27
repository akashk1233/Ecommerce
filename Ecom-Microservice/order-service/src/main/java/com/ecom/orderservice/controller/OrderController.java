package com.ecom.orderservice.controller;

import com.ecom.orderservice.dto.CreateOrderRequest;
import com.ecom.orderservice.service.CreateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private CreateOrder createOrderService;

    @PostMapping("/create")
    public ResponseEntity<Long> createOrder(@RequestBody CreateOrderRequest createOrderRequest) throws IllegalAccessException {
        return new ResponseEntity<>(createOrderService.createOrder(createOrderRequest), HttpStatus.CREATED);
    }
}
