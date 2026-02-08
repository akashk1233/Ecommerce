package com.ecom.inventoryservice.controller;

import com.ecom.inventoryservice.dto.InventoryResponse;
import com.ecom.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @GetMapping
    public ResponseEntity<List<InventoryResponse>> inventoryInfo(@RequestParam List<String> skuCode)
    {
        return new ResponseEntity<>(inventoryService.isInStock(skuCode),HttpStatus.OK);
    }
}
