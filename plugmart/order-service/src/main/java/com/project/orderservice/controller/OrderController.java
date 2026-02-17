package com.project.orderservice.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping
	public CompletableFuture<ResponseEntity<String>> placeOrder(
			@RequestBody OrderRequest orderRequest){
		
		return orderService.placeOrder(orderRequest)
	            .thenApply(response ->
	                    ResponseEntity.status(HttpStatus.CREATED)
	                            .body(response)
	            );
	}
	
}