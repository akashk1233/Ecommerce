package com.ecom.orderservice.controller;

import com.ecom.orderservice.dto.CreateOrderRequest;
import com.ecom.orderservice.service.CreateOrder;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {
    @Autowired
    private CreateOrder createOrderService;

    @PostMapping("/create")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    public ResponseEntity<Long> createOrder(@RequestBody CreateOrderRequest createOrderRequest) throws IllegalAccessException {
        return new ResponseEntity<>(createOrderService.createOrder(createOrderRequest), HttpStatus.CREATED);
    }

    public ResponseEntity<Long> fallBackMethod(
            CreateOrderRequest createOrderRequest,
            Throwable throwable) {

        log.info("Calling fallback due to: {}", throwable.getMessage());
        log.info("Exception method name {}", throwable.getClass().getName());

        return new ResponseEntity<>(-1L, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
