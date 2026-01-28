package com.ecom.orderservice.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderLineItemsDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderLineItemId;
    private String skuCode;
    private Long price;
    private int quantity;
}
