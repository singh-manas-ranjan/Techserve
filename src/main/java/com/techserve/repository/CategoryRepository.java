package com.techserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techserve.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
