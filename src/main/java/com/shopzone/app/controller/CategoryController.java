package com.shopzone.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopzone.app.dto.CategoryDto;
import com.shopzone.app.entity.Category;
import com.shopzone.app.service.CategoryService;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		log.info("In create category method for name " + category.getName());
		try {
			Category result = categoryService.createCategory(category);
			if (result.getId() != null) {
				return ResponseEntity.ok(category);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping("/get-all")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Category>> getAllCategories() {
		try {
			List<Category> categories = categoryService.getAllCategories();
			log.info("Categories fetched "+categories);
			return ResponseEntity.ok(categories);
		} catch (Exception e) {
			return ResponseEntity.noContent().build();
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<ResponseEntity> updateCategory(@RequestBody Category cat){
		String result=categoryService.updateCategory(cat);
		if(result=="updated") 
			return ResponseEntity.ok(null);
		return null;
	}
}
