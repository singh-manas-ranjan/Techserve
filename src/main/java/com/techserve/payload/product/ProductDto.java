package com.techserve.payload.product;

import org.hibernate.validator.constraints.Length;

import com.techserve.payload.category.CategoryDto;
import com.techserve.payload.stock.StockDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ProductDto {
	
	private int id;
	
	@NotBlank(message = "*required field")
	@Length(min = 2, max = 50)
	private String name;
	
	private String image;
	
	@NotBlank(message = "*required field")
	@Size(min = 100, max = 500)
	private String description;
	
	@NotBlank(message = "*required field")
	private double price;
	
	@Valid
	@NotBlank(message = "*required field")
	private StockDto stock;

	private CategoryDto category;
}
