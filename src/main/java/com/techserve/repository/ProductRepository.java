package com.techserve.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techserve.entities.Category;
import com.techserve.entities.Product;
import com.techserve.entities.User;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Product p WHERE name =:name ")
	Boolean isProductExistsByName(@Param("name") String name);
	
	Page<Product> findAllByCategory(Category category, Pageable pageable);
	
	Page<Product> findAllByUser(User user, Pageable pageable);
	
	Page<Product> findAllByNameContaining(String keyword, Pageable pageable);
}
