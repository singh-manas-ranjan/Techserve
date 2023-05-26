package com.techserve.service;

import java.security.Principal;
import java.util.List;

import com.techserve.payload.product.ProductDto;

public interface ProductService {
	
	ProductDto createProduct(ProductDto productDto,Integer categoryId, Principal principal);
	
	ProductDto getProductById(Integer productId);
	
	List<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String orderBy);
	
	List<ProductDto> getAllProductsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize, String sortBy, String orderBy);
	
	List<ProductDto> getAllProductsByUser(Integer pageNumber, Integer pageSize, String sortBy, String orderBy, Principal principal);
	
	List<ProductDto> getAllProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String orderBy);
	
	ProductDto updateProduct(ProductDto	 productDto, Principal principal);
	
	String deleteProductById(Integer productId, Principal principal);
}
