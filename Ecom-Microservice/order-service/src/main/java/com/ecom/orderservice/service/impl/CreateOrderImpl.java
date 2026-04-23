package com.ecom.orderservice.service.impl;

import com.ecom.orderservice.dto.CreateOrderRequest;
import com.ecom.orderservice.dto.InventoryResponse;
import com.ecom.orderservice.dto.OrderLineItemsDto;
import com.ecom.orderservice.event.OrderPlacedEvent;
import com.ecom.orderservice.exception.ListNotFoundException;
import com.ecom.orderservice.model.Order;
import com.ecom.orderservice.model.OrderLineItems;
import com.ecom.orderservice.repository.OrderRepo;
import com.ecom.orderservice.service.CreateOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CreateOrderImpl implements CreateOrder {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
    @Override
    public Long createOrder(CreateOrderRequest createOrderRequest) throws IllegalAccessException {
        List<OrderLineItems> order1 = getOrderLineItemList(createOrderRequest);
        Order order = Order.builder()
                .orderNumber(String.valueOf(UUID.randomUUID()))
                .orderLineItemsList(order1)
                .build();
        List<String> skuCodes = order1.stream().map(OrderLineItems::getSkuCode).toList();

        // check inventory for stock
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",uriBuilder ->
                        uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsInStock);

        if(allProductsInStock)
        {
            orderRepo.save(order);
            String resp = webClientBuilder.build().patch()
                            .uri("http://inventory-service/api/inventory",
                                    uriBuilder ->uriBuilder.queryParam("skuCodes",skuCodes).build()).toString();
            log.info("Invetory Status{}"+resp);
            kafkaTemplate.send("notificationTopic", OrderPlacedEvent.builder().orderNumber(order.getOrderNumber()).build());
        }
        else
            throw new IllegalArgumentException("item out of stock");
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
