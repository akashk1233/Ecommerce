package com.ecom.inventoryservice.controller;

import com.ecom.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @GetMapping("/{sku-code}")
    public ResponseEntity<Boolean> inventoryInfo(@PathVariable("sku-code") String skuCode)
    {
        return new ResponseEntity<>(inventoryService.isInStock(skuCode),HttpStatus.OK);
    }
}
