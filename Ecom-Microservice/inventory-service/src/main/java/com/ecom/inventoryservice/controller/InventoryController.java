package com.ecom.inventoryservice.controller;

import com.ecom.inventoryservice.dto.InventoryReservationResult;
import com.ecom.inventoryservice.dto.InventoryResponse;
import com.ecom.inventoryservice.service.InventoryService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @PostMapping
    @SneakyThrows // don't use this; not recommended -> here used to see exception
    public ResponseEntity<List<InventoryReservationResult>> inventoryInfo(@RequestBody Map<String,Integer> skuCode) {
//        log.info("wait started");
//        log.info("wait ended");
        return new ResponseEntity<>(inventoryService.isInStock(skuCode),HttpStatus.OK);
    }
    @PutMapping()
    public ResponseEntity<String> reduceQuantity(@RequestBody Map<String, Integer> skuCode){
        return new ResponseEntity<>(inventoryService.reduceQuantity(skuCode),HttpStatus.OK);
    }
}
