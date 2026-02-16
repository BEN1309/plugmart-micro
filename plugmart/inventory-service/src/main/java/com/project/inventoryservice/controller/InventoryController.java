package com.project.inventoryservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.inventoryservice.dto.InventoryResponse;
import com.project.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	// http://localhost:8083/api/inventory?sku-code=iphone13&sku-code=iphone13-red
	@GetMapping
	public ResponseEntity<List<InventoryResponse>> isInStock(
	        @RequestParam List<String> skuCode) {

	    return ResponseEntity.ok(inventoryService.isInStock(skuCode));
	}
	
}
