package com.shopzone.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopzone.app.dto.MerchantDto;
import com.shopzone.app.service.StoreService;

@RestController
@RequestMapping("/store")
public class StoreController {
	
	private static final Logger log=LoggerFactory.getLogger(StoreController.class);
	
	@Autowired
	private StoreService storeService;

	@GetMapping("/check")
	public String isStoreAvailableAndActive(@RequestHeader("X-Merchant-Slug") String merchantSlug) {
		log.info("Checking if store is available and active for merchant slug: {}", merchantSlug);
		try {
	String result=storeService.isStoreAvailableAndActive(merchantSlug);
		
	return result;
			
		}catch(Exception e) {
			return null;
		}
	}
	
	@GetMapping("/payment-methods")
	public List<String> getPaymentMethods(@RequestHeader("X-Merchant-Slug") String merchantSlug) {
		log.info("Fetching payment methods for merchant slug: {}", merchantSlug);
		return List.of("Online", "Cash");
	}
	
	@GetMapping("/slug")
	
	public List<MerchantDto> getMerchantSlug() {
		log.info("Fetching merchant slugs for landing page");
		return storeService.getAllActiveStore();
	}

}
