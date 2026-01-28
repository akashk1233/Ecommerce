package com.ecom.orderservice.service.impl;

import com.ecom.orderservice.dto.CreateOrderRequest;
import com.ecom.orderservice.dto.OrderLineItemsDto;
import com.ecom.orderservice.exception.ListNotFoundException;
import com.ecom.orderservice.model.Order;
import com.ecom.orderservice.model.OrderLineItems;
import com.ecom.orderservice.repository.OrderRepo;
import com.ecom.orderservice.service.CreateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CreateOrderImpl implements CreateOrder {
    @Autowired
    private OrderRepo orderRepo;
    @Override
    public Long createOrder(CreateOrderRequest createOrderRequest) throws IllegalAccessException {
        List<OrderLineItems> order1 = getOrderLineItemList(createOrderRequest);
        Order order = Order.builder()
                .orderNumber(String.valueOf(UUID.randomUUID()))
                .orderLineItemsList(order1)
                .build();
        orderRepo.save(order);
        return order.getOrderId();
    }

    private List<OrderLineItems> getOrderLineItemList(CreateOrderRequest createOrderRequest) throws IllegalAccessException {
        List<OrderLineItemsDto> dtoList = createOrderRequest.getOrderLineItemsDto();
        if(dtoList == null || dtoList.isEmpty())
        {
            throw new ListNotFoundException("list is empty");
        }
       return dtoList.stream().map(orderLineItemsDto ->
               OrderLineItems.builder()
                       .orderLineItemId(orderLineItemsDto.getOrderLineItemId())
                       .skuCode(orderLineItemsDto.getSkuCode())
                       .price(orderLineItemsDto.getPrice())
                       .quantity(orderLineItemsDto.getQuantity()).build()
               ).collect(Collectors.toList());
    }
}
