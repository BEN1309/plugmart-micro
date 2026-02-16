package com.project.productservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.productservice.dto.ProductRequest;
import com.project.productservice.dto.ProductResponse;
import com.project.productservice.model.Product;
import com.project.productservice.repository.ProductRepository;
import com.project.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
	
	@Override
	public void createProduct(ProductRequest productRequest) {
		
		log.info("Creating product with name {}", productRequest.getName());
		
		Product product = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
		
		productRepository.save(product);
		
		log.info("Product {} is saved", product.getId());
	}

	@Override
	public List<ProductResponse> getAllProducts() {
		
		log.info("Fetching all Products");
		
		List<ProductResponse> products = productRepository.findAll()
				.stream()
				.map(this::mapToProductResponse)
				.toList();

		log.info("Total product fetched: {}", products.size());
		
		return products;
	}
	
	private ProductResponse mapToProductResponse(Product product) {
	    return ProductResponse.builder()
	            .id(product.getId())
	            .name(product.getName())
	            .description(product.getDescription())
	            .price(product.getPrice())
	            .build();
}
}
