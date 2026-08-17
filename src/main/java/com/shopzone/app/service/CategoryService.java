package com.shopzone.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shopzone.app.entity.Category;
import com.shopzone.app.repo.CategoryRepo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	public Category createCategory(Category category) {
		try {
			category.setCreatedAt(LocalDateTime.now());
			return categoryRepo.save(category);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Category> getAllCategories() {
		try {
			return categoryRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String updateCategory(Category cat) {
		try {
			categoryRepo.save(cat);
			return "updated";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "no updatte";
		
	}

	

}
