package com.techserve.service;

import java.security.Principal;

import org.springframework.web.multipart.MultipartFile;

import com.techserve.payload.product.ProductDto;
import com.techserve.payload.product.ProductResponse;

public interface ProductService {
	
	ProductDto createProduct(ProductDto productDto, MultipartFile file, Integer categoryId, Principal principal);
	
	ProductDto getProductById(Integer productId);
	
	ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String orderBy);
	
	ProductResponse getAllProductsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize, String sortBy, String orderBy);
	
	ProductResponse getAllProductsByUser(Integer pageNumber, Integer pageSize, String sortBy, String orderBy, Principal principal);
	
	ProductResponse getAllProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String orderBy);
	
	ProductDto updateProduct(ProductDto	 productDto, MultipartFile file, Principal principal);
	
	String deleteProductById(Integer productId, Principal principal);
}
