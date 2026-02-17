package com.project.orderservice.service;

import java.util.concurrent.CompletableFuture;

import com.project.orderservice.dto.OrderRequest;

public interface OrderService {
	
	CompletableFuture<String> placeOrder(OrderRequest orderRequest);
	
}