package com.ecom.inventoryservice.controller;

import com.ecom.inventoryservice.dto.InventoryResponse;
import com.ecom.inventoryservice.service.InventoryService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @GetMapping
    @SneakyThrows // don't use this; not recommended -> here used to see exception
    public ResponseEntity<List<InventoryResponse>> inventoryInfo(@RequestParam List<String> skuCode) {
        log.info("wait started");
//        Thread.sleep(10000);
        log.info("wait ended");
        return new ResponseEntity<>(inventoryService.isInStock(skuCode),HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<String> reduceQuantity(@RequestParam List<String> skuCodes){
        return new ResponseEntity<>(inventoryService.reduceStock(skuCodes),HttpStatus.OK);
    }
}
