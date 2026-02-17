package com.project.orderservice.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.project.orderservice.dto.InventoryResponse;
import com.project.orderservice.dto.OrderLineItemsDto;
import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.event.OrderPlaceEvent;
import com.project.orderservice.model.Order;
import com.project.orderservice.model.OrderLineItems;
import com.project.orderservice.repository.OrderRepository;
import com.project.orderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
//import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;
	
	private final KafkaTemplate<String, OrderPlaceEvent> kafkaTemplate;

	@Override
	@Retry(name = "inventory")
	@CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
	//@TimeLimiter(name = "inventory")
	public CompletableFuture<String> placeOrder(OrderRequest orderRequest) {
		
		return CompletableFuture.supplyAsync(()->{
			
			Order order = new Order();
			order.setOrderNumber(UUID.randomUUID().toString());

			List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
														.stream()
														.map(this::mapToDto)
														.toList();

			order.setOrderLineItemsList(orderLineItems);
			
			List<String> skuCodes = order.getOrderLineItemsList()
				 .stream()
				 .map(orderLineItem-> orderLineItem.getSkuCode())
				 .toList();
			
			InventoryResponse[] inventoryResponsesArray =
			        webClientBuilder.build()
			                .get()
			                .uri(uriBuilder -> uriBuilder
			                        .scheme("http")
			                        .host("inventory-service")
			                        .path("/api/inventory")
			                        .queryParam("skuCode", skuCodes)
			                        .build())
			                .retrieve()
			                .bodyToMono(InventoryResponse[].class)
			                .block();



					
			boolean allProductsInStock = inventoryResponsesArray != null &&
			        Arrays.stream(inventoryResponsesArray)
			              .allMatch(InventoryResponse::isInStock);

			if (!allProductsInStock) {
			    throw new RuntimeException("Product not available in inventory");
			}

			orderRepository.save(order);
			
			kafkaTemplate.send("notificationTopic", new OrderPlaceEvent(order.getOrderNumber()));
			
			return "Order Placed Successfully";
			
		});

		
	}

	//mapToDto
	private OrderLineItems mapToDto(OrderLineItemsDto dto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setSkuCode(dto.getSkuCode());
		orderLineItems.setPrice(dto.getPrice());
		orderLineItems.setQuantity(dto.getQuantity());
		return orderLineItems;
	}

	//fallback Method
	public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, Throwable ex) {
	    throw new ResponseStatusException(
	            HttpStatus.SERVICE_UNAVAILABLE,
	            "INVENTORY-SERVICE is down or taking too long!"
	    );
	}


	
}