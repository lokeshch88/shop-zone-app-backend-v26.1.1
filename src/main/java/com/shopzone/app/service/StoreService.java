package com.shopzone.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopzone.app.dto.MerchantDto;
import com.shopzone.app.entity.Merchant;
import com.shopzone.app.repo.MerchantRepository;

@Service
public class StoreService {
	
	private static final Logger log = LoggerFactory.getLogger(StoreService.class);
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private MerchantRepository merchantRepository;
	

	public String isStoreAvailableAndActive(String merchantSlug) {
	
		log.info("Checking if store is available and active for merchantSlug: " + merchantSlug);
		try {
	Merchant merchant=	merchantService.getMerchantBySlug(merchantSlug)
//			.orElseThrow(() -> new IllegalArgumentException("Merchant not found for slug: " + merchantSlug))
			;
			
	if (merchant == null) {
				log.info("Merchant not found for slug: " + merchantSlug);
				return "Merchant not found";
			}
			if (merchant.getActive()) {
				log.info("Store is active for merchantSlug: " + merchantSlug);
				return "ACTIVE";
			} else {
				log.info("Store is not active for merchantSlug: " + merchantSlug);
				return "INACTIVE";
			}
		}
		catch (Exception e) {
			log.error("Error while checking store availability and status for merchantSlug: " + merchantSlug, e);
			return "Error: " + e.getMessage();
		}
	
	}


	public List<MerchantDto> getAllActiveStore() {
		List<Merchant> merchants = merchantRepository.findAllByActive(true);

		if (merchants == null || merchants.isEmpty()) {
			return java.util.Collections.emptyList();
		}

		return merchants.stream().map(m -> {
			MerchantDto dto = new MerchantDto();
			dto.setBusinessName(m.getBusinessName());
			dto.setStoreSlug(m.getStoreSlug());
			dto.setOwnerName(m.getOwnerName());
			return dto;
		}).collect(Collectors.toList());
	}
	
	

}
