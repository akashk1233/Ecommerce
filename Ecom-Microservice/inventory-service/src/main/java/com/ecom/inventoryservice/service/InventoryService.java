package com.ecom.inventoryservice.service;

import com.ecom.inventoryservice.dto.InventoryReservationResult;
import com.ecom.inventoryservice.dto.InventoryResponse;

import java.util.List;
import java.util.Map;

public interface InventoryService {

    List<InventoryReservationResult> isInStock(Map<String,Integer> skuCode);
    String reduceQuantity(Map<String, Integer> skuCodes);
}
