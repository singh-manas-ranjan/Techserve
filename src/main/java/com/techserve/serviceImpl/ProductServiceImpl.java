package com.techserve.serviceImpl;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tika.Tika;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techserve.CustomExceptions.InvalidFileFormatException;
import com.techserve.CustomExceptions.ResourceNotFoundException;
import com.techserve.entities.Category;
import com.techserve.entities.Product;
import com.techserve.entities.User;
import com.techserve.payload.product.ProductDto;
import com.techserve.payload.product.ProductResponse;
import com.techserve.repository.CategoryRepository;
import com.techserve.repository.ProductRepository;
import com.techserve.repository.UserRepository;
import com.techserve.service.ProductService;
import com.techserve.utils.CustomPageable;
import com.techserve.utils.ImageHelper;
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
	public ProductDto createProduct(ProductDto productDto, MultipartFile file, Integer categoryId, Principal principal) {
		Boolean isExists = productRepo.isProductExistsByName(productDto.getName());
		if(!isExists) {
			User user = userRepo.findByUsername(principal.getName());
			Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category_Id", categoryId));
			
			String detect = "";
			Tika tika = new Tika();
			try {
				detect = tika.detect(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String productImageName = "";
			if(detect.equals("image/jpeg"))
			{
				try {
					productImageName = new ImageHelper().saveImage(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				throw new InvalidFileFormatException(file.getOriginalFilename());
			}
			
			Product product = mapper.map(productDto, Product.class);
			product.setCategory(category);
			product.setUser(user);
			product.setImage(productImageName);
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
	public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String orderBy) {
		Pageable pageable = new CustomPageable().getPageable(pageNumber, pageSize, sortBy, orderBy);
		Page<Product> page = productRepo.findAll(pageable);
		List<ProductDto> dto = page.stream().map(product -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
		return new ProductResponse(dto, page);
	}

	@Override
	public ProductResponse getAllProductsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize, String sortBy, String orderBy) {
		
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Product", "Category_Id", categoryId));
		
		Pageable pageable = new CustomPageable().getPageable(pageNumber, pageSize, sortBy, orderBy);
		
		Page<Product> page = productRepo.findAllByCategory(category, pageable);
		
		List<ProductDto> dto = page.stream().map(product -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
		return  new ProductResponse(dto, page);
	}

	@Override
	public ProductResponse getAllProductsByUser(Integer pageNumber, Integer pageSize, String sortBy, String orderBy,Principal principal) {
		
		User user = userRepo.findByUsername(principal.getName());
		
		Pageable pageable = new CustomPageable().getPageable(pageNumber, pageSize, sortBy, orderBy);
		
		Page<Product> page = productRepo.findAllByUser(user, pageable);
		
		List<ProductDto> dto = page.stream().map(product -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
		return  new ProductResponse(dto, page);
	}
	
	@Override
	public ProductResponse getAllProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String orderBy) {
		Pageable pageable = new CustomPageable().getPageable(pageNumber, pageSize, sortBy, orderBy);
		Page<Product> page = productRepo.findAllByNameContaining(keyword, pageable);
		List<ProductDto> dto = page.stream().map(product  -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
		return  new ProductResponse(dto, page);
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto,MultipartFile file, Principal principal) {
		Product oldProduct = productRepo.findById(productDto.getId()).orElseThrow(()-> new ResourceNotFoundException("Product", "Product_Id", productDto.getId()));
		Tika tika = new Tika();
		String fileExtension="";
		String productImage = "";
		
		if( !file.getOriginalFilename().equals(oldProduct.getImage()))
		{
				try {
					fileExtension = tika.detect(file.getBytes());
					if(fileExtension.equals("image/jpeg")) 
					{
						productImage = new ImageHelper().saveImage(file);
					}
					else
					{
						throw new InvalidFileFormatException(file.getOriginalFilename());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		

		if(oldProduct.getUser().getUsername().equals(principal.getName())) {
			Product p = mapper.map(productDto, Product.class);
			p.setUser(oldProduct.getUser());
			if(!productImage.equals("")) {
				p.setImage(productImage);
			}
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
