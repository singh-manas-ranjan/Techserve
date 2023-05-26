package com.techserve.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CustomPageable {
	public Pageable getPageable(Integer pageNumber, Integer pageSize, String sortBy, String orderby) {
		
		pageNumber = pageNumber >= 1 ? pageNumber -1 : 0;
		
		Sort sort = sortBy.equalsIgnoreCase("desc")? Sort.by(orderby).descending() : Sort.by(orderby).ascending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		return pageable;
	}
}
