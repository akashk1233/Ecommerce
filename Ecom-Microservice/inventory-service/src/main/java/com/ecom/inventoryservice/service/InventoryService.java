package com.ecom.inventoryservice.service;

import com.ecom.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {

    List<InventoryResponse> isInStock(List<String> skuCode);
    String reduceStock(List<String> skuCode);
}
