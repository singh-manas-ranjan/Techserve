package com.techserve.payload.product;

import java.util.List;

import org.springframework.data.domain.Page;

import com.techserve.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductResponse {

	private List<ProductDto> productDto;
	private Page<Product> page;
}
