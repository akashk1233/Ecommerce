package com.ecom.inventoryservice.service.impl;

import com.ecom.inventoryservice.dto.InventoryResponse;
import com.ecom.inventoryservice.repository.InventoryRepo;
import com.ecom.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;
    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {

        return inventoryRepo.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder().skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()>0)
                            .build()
                ).toList();

    }
}
