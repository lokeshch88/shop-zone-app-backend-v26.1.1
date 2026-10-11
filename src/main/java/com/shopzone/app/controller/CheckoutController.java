package com.shopzone.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopzone.app.service.CheckoutService;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
@RequestMapping("/checkout")
//delivery/estimate/${addressId}
public class CheckoutController {

	private static final Logger log = LoggerFactory.getLogger(CheckoutController.class);
	
	@Autowired
	private CheckoutService checkoutService;

	@GetMapping("/delivery/estimate/{id}")
	public ResponseEntity<Map<String, LocalDate>> getDeliveryEstimateDate(@PathVariable Long id,
			@RequestHeader("X-Merchant-Slug") String merchantSlug) {
		log.info("In fetch estimate time for delivery");
		
		Map<String, LocalDate> resp=checkoutService.getDeliveryExpDate(id, merchantSlug);
		
		return ResponseEntity.ok(resp);
	}
	
	
}
