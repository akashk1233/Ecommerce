package com.ecom.orderservice.service;

import com.ecom.orderservice.dto.CreateOrderRequest;

public interface CreateOrder {

    Long createOrder(CreateOrderRequest createOrderRequest) throws IllegalAccessException;
}
