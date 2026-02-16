package com.project.inventoryservice.service;

import java.util.List;

import com.project.inventoryservice.dto.InventoryResponse;

public interface InventoryService {
	
	List<InventoryResponse> isInStock(List<String> skuCode);
	
}
