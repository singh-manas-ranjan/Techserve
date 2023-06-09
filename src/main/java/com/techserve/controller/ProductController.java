package com.techserve.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techserve.payload.product.ProductDto;
import com.techserve.payload.product.ProductResponse;
import com.techserve.payload.stock.StockDto;
import com.techserve.serviceImpl.ProductServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductServiceImpl productService;
	
	@GetMapping("/add-product")
	public String addProductForm(Model model) {
		
		ProductDto productDto = new ProductDto();
		StockDto stockDto = new StockDto();
		productDto.setStock(stockDto);
		
		model.addAttribute("title", "Add products");
		model.addAttribute("productDto", productDto);
		
		return "user/addProductForm";
	}
	
	@PostMapping("/add-products")
	public String processAddProductForm(@Valid @ModelAttribute ProductDto productDto, BindingResult result, @RequestParam Integer categoryId,@RequestParam MultipartFile file, Principal principal, Model model, RedirectAttributes redirAttr) {
		if(result.hasErrors()) {
			return "user/addProductForm";
		}
		if(file.isEmpty() || file == null) {
			model.addAttribute("multipartMsg", "Upload Product Image");
			return "user/addProductForm";
		}
		else {
			ProductDto createProduct = productService.createProduct(productDto, file, categoryId, principal);
			if(createProduct != null) {
				redirAttr.addFlashAttribute("addProductMsg", "Product Added Successfully");
				return "redirect:/products/add-product";
			}
			else {
				redirAttr.addFlashAttribute("addProductMsg", "Something Went Wrong, Try Again Later");
				return "redirect:/products/add-product";
			}
		}
	}
	
	@GetMapping("/id")
	public String getProductById(@RequestParam Integer productId, Model model) {
		ProductDto productDto = productService.getProductById(productId);
		model.addAttribute("title", productDto.getName());
		model.addAttribute("productDto", productDto);
		return "user/seeProduct";
	}
	
	@GetMapping
	public String getAllProducts(@RequestParam(name = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
			@RequestParam(name = "pageSize",defaultValue = "6", required = false) Integer pageSize,
			@RequestParam(name = "orderBy",defaultValue = "id", required = false) String id,
			@RequestParam(name = "sortBy",defaultValue = "asc", required = false) String sortBy, Model model) {
		
		model.addAttribute("title", "All Products");
		ProductResponse allProducts = productService.getAllProducts(pageNumber, pageSize, sortBy, sortBy);
		if(allProducts.getProductDto().size() > 0)
		{ 
			model.addAttribute("allProductsByCategory", allProducts.getProductDto());
			model.addAttribute("page", allProducts.getPage());
			return "user/allProducts";
		}
		else
		{
			model.addAttribute("allProductsMsg", "No product Found");
			model.addAttribute("allProductsByCategory", allProducts);
			return "user/allProducts";
		}
	}
	
	@GetMapping("/category")
	public String getAllProductsByCategory(@RequestParam Integer categoryId,
			@RequestParam(name = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
			@RequestParam(name = "pageSize",defaultValue = "6", required = false) Integer pageSize,
			@RequestParam(name = "orderBy",defaultValue = "id", required = false) String orderBy,
			@RequestParam(name = "sortBy",defaultValue = "asc", required = false) String sortBy,Model model) {
		
		model.addAttribute("title", "All Products");
		ProductResponse allproductsByCategory = productService.getAllProductsByCategory(categoryId, pageNumber, pageSize, sortBy, orderBy);
		if(allproductsByCategory.getProductDto().size() > 0)
		{ 
			model.addAttribute("allProductsByCategory", allproductsByCategory);
			model.addAttribute("page", allproductsByCategory.getPage());
			return "user/allProducts";
		}
		else
		{
			model.addAttribute("allProductsByCategoryMsg", "No product Found");
			model.addAttribute("allProductsByCategory", allproductsByCategory);
			return "user/allProducts";
		}
	}
	
	@GetMapping("/search")
	public String findProductBbyKeyword(@RequestParam String keyword,
			@RequestParam(name = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
			@RequestParam(name = "pageSize",defaultValue = "6",required = false) Integer pageSize,
			@RequestParam(name = "orderBy",defaultValue = "id", required = false) String orderBy,
			@RequestParam(name = "sortBy", defaultValue = "asc",required = false) String sortBy,
			Model model) {
		ProductResponse allProductsByKeyword = productService.getAllProductsByKeyword(keyword, pageNumber, pageSize, sortBy, orderBy);
		if(allProductsByKeyword.getProductDto().size() > 0) 
		{
			model.addAttribute("allProductsByUser", allProductsByKeyword);
			model.addAttribute("page", allProductsByKeyword.getPage());
			return "user/allProducts";
		}
		else
		{
			model.addAttribute("allProductsByKeywordMsg", "No Product Found");
			model.addAttribute("allProductsByKeyword", allProductsByKeyword);
			return"user/allProducts";
		}
	}
	
	@GetMapping("/manage-products")
	public String manageProductsBySeller(@RequestParam(name = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "6", required = false) Integer pageSize,
			@RequestParam(name = "orderBy", defaultValue = "id", required = false)String orderBy,
			@RequestParam(name = "pageNumber", defaultValue = "asc", required = false) String sortBy,Model model, Principal principal) {
		
		model.addAttribute("title", "Manage products");
		ProductResponse allProductsByUser = productService.getAllProductsByUser(pageNumber, pageSize, sortBy, orderBy, principal);
		if(allProductsByUser.getProductDto().size() > 0) 
		{
			model.addAttribute("allProductsByUser", allProductsByUser);
			model.addAttribute("page", allProductsByUser.getPage());
			return "user/allProducts";
		}
		else
		{
			model.addAttribute("manageProductMsg", "No Product Found");
			model.addAttribute("allProductsByUser", allProductsByUser);
			return"user/allProducts";
		}
	}
	
	@GetMapping("/update-product")
	public String updateproductForm(@RequestParam Integer productId, Model model) {
		model.addAttribute("title", "Update product");
		ProductDto productDto = productService.getProductById(productId);
		model.addAttribute("productDto", productDto);
		return"addProductForm";
	}
	
	@PostMapping("/update-product")
	public String processUpdateProductForm(@Valid @ModelAttribute ProductDto productdto, BindingResult result,@RequestParam Integer productId, @RequestParam MultipartFile file, Principal principal, RedirectAttributes redirAttr) {

		if(result.hasErrors()) {
			return "user/addProductForm";
		}
		else {
			ProductDto updateProduct = productService.updateProduct(productdto, file,  principal);
			if(updateProduct != null) {
				redirAttr.addFlashAttribute("updateProductMsg", "Product Updated Successfully");
				return "redirect:/products/manage-producst";
			}
			else {
				redirAttr.addFlashAttribute("updateProductMsg", "Something Went Wrong, Try Again Later");
				return "redirect:/products/manage-product";
			}
		}
	}
}
