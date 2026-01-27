package com.ecom.inventoryservice.service.impl;

import com.ecom.inventoryservice.repository.InventoryRepo;
import com.ecom.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;
    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {

        return inventoryRepo.findBySkuCode(skuCode).isPresent();
    }
}
