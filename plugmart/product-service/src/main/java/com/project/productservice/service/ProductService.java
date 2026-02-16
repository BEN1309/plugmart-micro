package com.project.productservice.service;

import java.util.List;

import com.project.productservice.dto.ProductRequest;
import com.project.productservice.dto.ProductResponse;

public interface ProductService {

	void createProduct(ProductRequest productRequest);
	
	List<ProductResponse> getAllProducts();
}
