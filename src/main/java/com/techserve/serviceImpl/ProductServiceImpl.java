package com.techserve.serviceImpl;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techserve.CustomExceptions.ResourceNotFoundException;
import com.techserve.entities.Category;
import com.techserve.entities.Product;
import com.techserve.entities.User;
import com.techserve.payload.product.ProductDto;
import com.techserve.repository.CategoryRepository;
import com.techserve.repository.ProductRepository;
import com.techserve.repository.UserRepository;
import com.techserve.service.ProductService;
import com.techserve.utils.CustomPageable;
@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public ProductDto createProduct(ProductDto productDto,Integer categoryId, Principal principal) {
		Boolean isExists = productRepo.isProductExistsByName(productDto.getName());
		if(!isExists) {
			User user = userRepo.findByUsername(principal.getName());
			Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category_Id", categoryId));
			Product product = mapper.map(productDto, Product.class);
			product.setCategory(category);
			product.setUser(user);
			return mapper.map(productRepo.save(product), ProductDto.class);
		}
		return null;
	}

	@Override
	public ProductDto getProductById(Integer productId) {
		Product returnedProduct = productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "Product_Id", productId));
		return mapper.map(returnedProduct, ProductDto.class);
	}

	@Override
	public List<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String orderBy) {
		Pageable pageable = new CustomPageable().getPageable(pageNumber, pageSize, sortBy, orderBy);
		Page<Product> page = productRepo.findAll(pageable);
		List<ProductDto> dto = page.stream().map(product -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
		return dto;
	}

	@Override
	public List<ProductDto> getAllProductsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize, String sortBy, String orderBy) {
		
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Product", "Category_Id", categoryId));
		
		Pageable pageable = new CustomPageable().getPageable(pageNumber, pageSize, sortBy, orderBy);
		
		Page<Product> page = productRepo.findAllByCategory(category, pageable);
		
		List<ProductDto> dto = page.stream().map(product -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
		return dto;
	}

	@Override
	public List<ProductDto> getAllProductsByUser(Integer pageNumber, Integer pageSize, String sortBy, String orderBy,Principal principal) {
		
		User user = userRepo.findByUsername(principal.getName());
		
		Pageable pageable = new CustomPageable().getPageable(pageNumber, pageSize, sortBy, orderBy);
		
		Page<Product> allByUser = productRepo.findAllByUser(user, pageable);
		
		List<ProductDto> dto = allByUser.stream().map(product -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
		return dto;
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto, Principal principal) {
		Product oldProduct = productRepo.findById(productDto.getId()).orElseThrow(()-> new ResourceNotFoundException("Product", "Product_Id", productDto.getId()));
		if(oldProduct.getUser().getUsername().equals(principal.getName())) {
			Product p = mapper.map(productDto, Product.class);
			p.setUser(oldProduct.getUser());
			return mapper.map(productRepo.save(p),ProductDto.class);
		}
		else {
			throw new ResourceNotFoundException("Product", "Product_Id", productDto.getId());
		}
	}

	@Override
	public String deleteProductById(Integer productId, Principal principal) {
		Product p = productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "Product_Id", productId));
		if(p.getUser().getUsername().equals(principal.getName())) {
			productRepo.delete(p);
			return "Product with Id : "+productId+" Deleted Successfully";
		}
		else {
			throw new ResourceNotFoundException("Product", "Product_Id", productId);
		}
	}

}
