package com.shopzone.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shopzone.app.controller.CategoryController;
import com.shopzone.app.dto.CategoryDto;
import com.shopzone.app.entity.Category;
import com.shopzone.app.entity.Merchant;
import com.shopzone.app.entity.User;
import com.shopzone.app.repo.CategoryRepo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private MerchantService merchantservice;
	
	
	private static final Logger log = LoggerFactory.getLogger(CategoryService.class);


	public Category createCategory(Category category, String merchantSlug) {
		log.info("In create category service for category name " + category.getName() + " merchantSlug: " + merchantSlug);
		
		try {
//			authenticationService.getCurrentMerchant().ifPresent(merchant -> {
//				category.setMerchant(merchant);
//				category.setCreatedAt(LocalDateTime.now());
//				categoryRepo.save(category);
//			});
			category.setCreatedAt(LocalDateTime.now());
		 User user=	authenticationService.getCurrentUser();
				category.setCreatedBy(user.getUsername());
			Merchant merchant =user.getMerchant();
			category.setCreatedBy(user.getUsername());
			category.setMerchant(merchant);
		log.info("Creating category with name: " + category.getName() + " for merchant: " + merchant.getBusinessName()+ " by user: " + user.getUsername());
			return categoryRepo.save(category);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<CategoryDto> getAllCategories(String merchantSlug) {
		
		log.info("Fetching all categories for merchant slug: " + merchantSlug);
		
		try {
			Merchant merchant=	merchantservice.getMerchantBySlug(merchantSlug);

		List<Category>	 list =categoryRepo.findAllByMerchant(merchant);
			return list.stream().map(CategoryDto::new).collect(Collectors.toList());

			
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
