package com.ecom.inventoryservice.service.impl;

import com.ecom.inventoryservice.dto.InventoryReservationResult;
import com.ecom.inventoryservice.dto.InventoryResponse;
import com.ecom.inventoryservice.model.Inventory;
import com.ecom.inventoryservice.repository.InventoryRepo;
import com.ecom.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;
    @Override
    @Transactional(readOnly = true)
    public List<InventoryReservationResult> isInStock(Map<String,Integer> skuCode) {

//        return inventoryRepo.findBySkuCodeIn(skuCode.keySet().stream().toList()).stream()
//                .map(inventory ->
//                    InventoryResponse.builder().skuCode(inventory.getSkuCode())
//                            .isInStock(inventory.getQuantity()>=skuCode.get(inventory.getSkuCode()))
//                            .build()
//                ).toList();

        List<Inventory> inventories = inventoryRepo.findBySkuCodeIn(skuCode.keySet().stream().toList());

        Map<String, Inventory> inventoryMap = inventories.stream()
                .collect(Collectors.toMap(Inventory::getSkuCode, i-> i));
        List<InventoryReservationResult> result = new ArrayList<>();
        boolean allSuccess = true;
        for(Map.Entry<String, Integer> entry : skuCode.entrySet()){

            String sku = entry.getKey();
            int reqQuantity = entry.getValue();

            Inventory inventory = inventoryMap.get(sku);

            if(inventory == null){
                result.add(InventoryReservationResult.builder()
                                .success(false)
                                .message("skuCode not found")
                                .skuCode(sku)
                        .build());
                allSuccess = false;

            }
            else if(inventory.getQuantity() < reqQuantity){
                result.add(InventoryReservationResult.builder()
                                .skuCode(sku)
                                .success(false)
                                .message("skuCode Item not in stock")
                        .build());
                allSuccess = false;

            }
            else {
                result.add(InventoryReservationResult.builder()
                                .message("Reserved, -> skuCode {} item is in stock "+sku)
                                .skuCode(sku)
                                .success(true)
                        .build());
            }

        }
        return result;
    }

    public String reduceQuantity(Map<String,Integer> skuCodes){
        List<Inventory> inventories = inventoryRepo.findBySkuCodeIn(skuCodes.keySet().stream().toList());
        inventories.forEach(eachInventory -> 
            eachInventory
            .setQuantity(eachInventory.getQuantity() - skuCodes.get(eachInventory.getSkuCode())));
        inventoryRepo.saveAll(inventories);
        return "Inventory Updated Sucessfully";
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
