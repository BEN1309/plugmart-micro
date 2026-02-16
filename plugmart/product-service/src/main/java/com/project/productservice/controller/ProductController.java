package com.project.productservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.productservice.dto.ProductRequest;
import com.project.productservice.dto.ProductResponse;
import com.project.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest) {

		productService.createProduct(productRequest);

		return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully");
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts() {

		List<ProductResponse> allProducts = productService.getAllProducts();

		return ResponseEntity.ok(allProducts);

	}

}
