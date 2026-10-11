package com.shopzone.app.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shopzone.app.dto.MerchantDto;
import com.shopzone.app.dto.MerchantPublicDto;
import com.shopzone.app.dto.UserDto;
import com.shopzone.app.entity.Merchant;
import com.shopzone.app.entity.MerchantStatus;
import com.shopzone.app.entity.SubscriptionPlan;
import com.shopzone.app.entity.SubscriptionStatus;
import com.shopzone.app.repo.MerchantRepository;

import ch.qos.logback.classic.Logger;

@Service
//@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final ModelMapper modelMapper;
	private final UserService userService;
    
    private static final org.slf4j.Logger log=LoggerFactory.getLogger(MerchantService.class);	
    

    public MerchantService(MerchantRepository merchantRepository, ModelMapper modelMapper, UserService userService) {
		super();
		this.merchantRepository = merchantRepository;
		this.modelMapper = modelMapper;
		this.userService = userService;
	}

	public MerchantDto createMerchant(MerchantDto dto) {

		log.info("Creating merchant for", dto);
		try {
        if (merchantRepository.existsByStoreSlug(dto.getStoreSlug())) {
            throw new RuntimeException("Store slug already exists");
        }

        if (dto.getCustomDomain() != null &&
            merchantRepository.existsByCustomDomain(dto.getCustomDomain())) {
            throw new RuntimeException("Custom domain already exists");
        }
        
        log.info("Checking if username exists for Admin user: " + dto.getUsername());
        if(dto.getUsername()!=null && userService.existsByUsername(dto.getUsername())) {
			log.info("Username already exists for Admin user: " + dto.getUsername());
			throw new RuntimeException("Username already exists");
		}
        
//        log.info("Checking if user exists for email: " + dto.getEmail());

        Merchant merchant =
                modelMapper.map(dto, Merchant.class);

        merchant.setMerchantCode(
                generateMerchantCode()
        );

        merchant.setSubscriptionPlan(
                SubscriptionPlan.FREE
        );

        merchant.setSubscriptionStatus(
                SubscriptionStatus.TRIAL
        );

        merchant.setStatus(
                MerchantStatus.ACTIVE
        );

        merchant.setActive(true);

        initializeLimits(merchant);

        Merchant savedMerchant =
                merchantRepository.save(merchant); 
        
        //register for admin user
        if(dto.getUsername()!= null && dto.getPassword() != null) {
			//register admin user
        	log.info("Registering admin user for merchant: {}", savedMerchant.getMerchantCode());
        	UserDto userDto = new UserDto();
        	userDto.setUsername(dto.getUsername());
        	userDto.setPassword(dto.getPassword());
        	userDto.setEmail(dto.getOwnerEmail());
        	userDto.setFirstName(dto.getOwnerName());
        	userDto.setUserRole("ADMIN");
        	
			String usermsg=userService.registerUser(userDto, savedMerchant);
		}


        return modelMapper.map(savedMerchant, MerchantDto.class);
		}
		catch (Exception e) {
	MerchantDto errorDto = new MerchantDto();
			log.error("Error creating merchant: {}", e.getMessage());
			errorDto.setMsg("Error : " + e.getMessage());
			return errorDto;
		}
    }

    public MerchantDto getMerchant(Long id) {

        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Merchant not found"));

        return modelMapper.map(
                merchant,
                MerchantDto.class
        );
    }

    public MerchantDto updateMerchant(
            Long id,
            MerchantDto dto) {

        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Merchant not found"));

        merchant.setBusinessName(dto.getBusinessName());
        merchant.setDescription(dto.getDescription());
        merchant.setOwnerName(dto.getOwnerName());
        merchant.setOwnerPhone(dto.getOwnerPhone());

        merchant.setAddressLine1(dto.getAddressLine1());
        merchant.setAddressLine2(dto.getAddressLine2());
        merchant.setCity(dto.getCity());
        merchant.setState(dto.getState());
        merchant.setPincode(dto.getPincode());

        merchant.setLogoUrl(dto.getLogoUrl());

//        merchant.setPrimaryColor(dto.getPrimaryColor());
//        merchant.setSecondaryColor(dto.getSecondaryColor());

        merchant.setInventoryEnabled(dto.getInventoryEnabled());
        merchant.setDeliveryEnabled(dto.getDeliveryEnabled());
        merchant.setOnlinePaymentEnabled(
                dto.getOnlinePaymentEnabled()
        );

        Merchant updated =
                merchantRepository.save(merchant);

        return modelMapper.map(
                updated,
                MerchantDto.class
        );
    }

    public List<MerchantDto> getAllMerchants() {

        return merchantRepository.findAll()
                .stream()
                .map(m -> modelMapper.map(m, MerchantDto.class))
                .collect(Collectors.toList());
    }

    public void deactivateMerchant(Long id) {

        Merchant merchant =
                merchantRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Merchant not found"));

        merchant.setActive(false);
        merchant.setStatus(
                MerchantStatus.INACTIVE
        );

        merchantRepository.save(merchant);
    }

    private void initializeLimits(Merchant merchant) {

        switch (merchant.getSubscriptionPlan()) {

            case FREE:
                merchant.setMaxProducts(50L);
                merchant.setMaxUsers(2L);
                merchant.setMaxOrdersPerMonth(100L);
                merchant.setMaxStorageMb(500L);
                merchant.setMaxApiRequestsPerMonth(10000L);
                break;

            case STARTER:
                merchant.setMaxProducts(500L);
                merchant.setMaxUsers(10L);
                merchant.setMaxOrdersPerMonth(2000L);
                merchant.setMaxStorageMb(5000L);
                merchant.setMaxApiRequestsPerMonth(100000L);
                break;

            case PROFESSIONAL:
                merchant.setMaxProducts(5000L);
                merchant.setMaxUsers(50L);
                merchant.setMaxOrdersPerMonth(10000L);
                merchant.setMaxStorageMb(25000L);
                merchant.setMaxApiRequestsPerMonth(500000L);
                break;

            case BUSINESS:
                merchant.setMaxProducts(20000L);
                merchant.setMaxUsers(200L);
                merchant.setMaxOrdersPerMonth(50000L);
                merchant.setMaxStorageMb(100000L);
                merchant.setMaxApiRequestsPerMonth(2000000L);
                break;

            case ENTERPRISE:
                merchant.setMaxProducts(Long.MAX_VALUE);
                merchant.setMaxUsers(Long.MAX_VALUE);
                merchant.setMaxOrdersPerMonth(Long.MAX_VALUE);
                merchant.setMaxStorageMb(Long.MAX_VALUE);
                merchant.setMaxApiRequestsPerMonth(Long.MAX_VALUE);
                break;
        }
    }

    private String generateMerchantCode() {

        return "MRC" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 5)
                        .toUpperCase();
    }

	public Merchant getMerchantBySlug(String merchantSlug) {
	
		return merchantRepository.findByStoreSlug(merchantSlug)
				.orElseThrow(() -> new RuntimeException("Merchant with slug " + merchantSlug + " not found"));
	}

	public boolean isStoreSlugAvailable(String slug) {
		
		return !merchantRepository.existsByStoreSlug(slug);
		
	}
	
	public MerchantPublicDto getPublicMerchantDetailsBySlug(String merchantSlug) {
		
//		merchantRepository.findByStoreSlug(merchantSlug)
//				.orElseThrow(() -> new RuntimeException("Merchant with slug " + merchantSlug + " not found"));
//		
		return merchantRepository.findByStoreSlug(merchantSlug)
				.map(merchant -> modelMapper.map(merchant, MerchantPublicDto.class))
				.orElseThrow(() -> new RuntimeException("Merchant with slug " + merchantSlug + " not found"));
	}


    
    
}