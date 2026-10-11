package com.shopzone.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopzone.app.dto.MerchantDto;
import com.shopzone.app.dto.MerchantPublicDto;
import com.shopzone.app.service.EmailService;
import com.shopzone.app.service.MerchantService;

@RestController
@RequestMapping("/api/merchants")
//@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;
    
	private static final Logger log = LoggerFactory.getLogger(MerchantController.class);

    public MerchantController(MerchantService merchantService) {
		super();
		this.merchantService = merchantService;
	}

	@PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MerchantDto> createMerchant(
            @RequestBody MerchantDto dto) {
		
		MerchantDto response= merchantService.createMerchant(dto);

				if (response != null && response.getId() != null ) {
					log.info("Merchant created successfully with ID: " + response.getId());
					return ResponseEntity.status(HttpStatus.CREATED).body(response);
				} else {
					log.error("Failed to create merchant");
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
				}
	
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MerchantDto> getMerchant(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                merchantService.getMerchant(id)
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<MerchantDto>> getAllMerchants() {
    	log.info("Fetching all merchants");

        return ResponseEntity.ok(
                merchantService.getAllMerchants()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MerchantDto> updateMerchant(
            @PathVariable Long id,
            @RequestBody MerchantDto dto) {
log.info("In Merchant Controller for Updating merchant with ID: " + id);
        return ResponseEntity.ok(
                merchantService.updateMerchant(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateMerchant(
            @PathVariable Long id) {

        merchantService.deactivateMerchant(id);

        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/check-slug") 
    public ResponseEntity<Boolean> checkStoreSlugAvailability(
			@RequestParam String slug) {

    	log.info("Checking store slug availability for slug: " + slug);
		boolean isAvailable = merchantService.isStoreSlugAvailable(slug);
		
		log.info("Store slug availability for slug '" + slug + "': " + isAvailable);	

		return ResponseEntity.ok(isAvailable);
	}
    
//    @GetMapping("/deatails")
    
    
    @GetMapping("/by-slug")
    public ResponseEntity<MerchantPublicDto> getMerchantBySlug(
			@RequestHeader(value = "X-Merchant-Slug", required = true) String slug) {

		log.info("Fetching merchant public details for slug: " + slug);

		return ResponseEntity.ok(
				merchantService.getPublicMerchantDetailsBySlug(slug)
		);
	}
    
}