package com.ecom.inventoryservice.service.impl;

import com.ecom.inventoryservice.dto.InventoryResponse;
import com.ecom.inventoryservice.model.Inventory;
import com.ecom.inventoryservice.repository.InventoryRepo;
import com.ecom.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public String reduceStock(List<String> skuCodes) {
        List<Inventory> inventries = inventoryRepo.findBySkuCodeIn(skuCodes);
        if(inventries.stream().anyMatch(inv->inv.getQuantity() < 0)) return "Item Is Out Of stock";
        inventries.forEach(inventory-> inventory.setQuantity(inventory.getQuantity() - 1));
        inventoryRepo.saveAll(inventries);
        return "Quantity Updated Successfully";
    }
}
