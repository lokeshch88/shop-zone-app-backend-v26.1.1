package com.shopzone.app.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shopzone.app.controller.UserController;
import com.shopzone.app.dto.ProductDto;
import com.shopzone.app.dto.UserDto;
import com.shopzone.app.dto.VariantDTO;
import com.shopzone.app.dto.VariantImageDTO;
import com.shopzone.app.entity.Category;
import com.shopzone.app.entity.Merchant;
import com.shopzone.app.entity.Product;
import com.shopzone.app.entity.ProductImage;
import com.shopzone.app.entity.ProductVariant;
import com.shopzone.app.entity.User;
import com.shopzone.app.entity.VariantImage;
import com.shopzone.app.repo.CategoryRepo;
import com.shopzone.app.repo.ProductImageRepository;
import com.shopzone.app.repo.ProductRepository;
import com.shopzone.app.repo.ProductVariantRepository;
import com.shopzone.app.repo.VariantImageRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private CategoryRepo categoryRepository;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private ProductImageStorageService imageStorageService;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private VariantImageRepository variantImageRepository;

	@Autowired
	private ProductVariantRepository productVariantRepository;

	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

//	public ProductDto createProduct(ProductDto productDto, MultipartFile imageFile, String merchantSlug) {
//		log.info("In create new product service for name " +productDto.getName() +" obj: "+ productDto);
//		try {
//			if (productDto.getDiscountPrice() != null
//					&& productDto.getMrp().compareTo(productDto.getDiscountPrice()) < 0) {
//				throw new IllegalArgumentException("Discount price cannot be greater than MRP.");
//			}
//
//			// Map DTO to Entity
//			Product product = modelMapper.map(productDto, Product.class);
//			product.setCategoryId(categoryRepo.findById(productDto.getCategoryId())
//					.orElseThrow(() -> new RuntimeException("Category not found")));
//
//			  if (imageFile != null && !imageFile.isEmpty()) 
//		            product.setImageData(imageFile.getBytes());
//			product.setCreatedAt(LocalDateTime.now());
////			product.setIsActive("Y");
//
//			// Map & set product in each variant
//			if (product.getVariants() != null) {
//				for (ProductVariant variant : product.getVariants()) {
//					variant.setProduct(product); // IMPORTANT for bidirectional mapping
//				}
//			}
//
//			log.info("Product obj to save "+product.toString());
//			// Save product (with cascade it will save variants too)
//			
//			//admin authentication
//			User user = authenticationService.getCurrentUser();
//			Merchant merchant = user.getMerchant();
//			
//			//verify slug
//			if (!merchant.getStoreSlug().equals(merchantSlug)) {
//				throw new IllegalArgumentException("Merchant slug does not match the authenticated user's merchant.");
//			}
//			
//			log.info("Creating product: " + product.getName() +" for merchant: " + merchant.getId()+" by user: "+user.getUsername());
//		product.setMerchant(merchant);
//		product.setCreatedBy(user.getUsername());
//			Product savedProduct = productRepository.save(product);
//
//			return modelMapper.map(savedProduct, ProductDto.class);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	// muti images logic -without category mandatory
	public ProductDto createProduct(ProductDto productDto, MultipartFile[] imageFiles,
//	        MultipartFile[][] variantImageFiles,
			org.springframework.web.multipart.MultipartHttpServletRequest request, String merchantSlug) {

		log.info("Creating product: {}", productDto.getName() + ", " + productDto.toString());

		try {

			// ==========================================
			// 1. CURRENT USER
			// ==========================================

			User user = authenticationService.getCurrentUser();
			log.info("Authenticated user: " + user.getUsername() + " for merchant slug: " + merchantSlug);

			if (user == null) {
				log.error("Authenticated user not found for merchant slug: " + merchantSlug);
				throw new RuntimeException("Authenticated user not found");
			}

			// ==========================================
			// 2. MERCHANT
			// ==========================================

			Merchant merchant = user.getMerchant();
			log.info("User {} is associated with merchant id: {}", user.getUsername(),
					merchant != null ? merchant.getId() : "null" + " for product: " + productDto.getName());

			if (merchant == null) {
				throw new RuntimeException("User is not associated with merchant");
			}

			// Max products check
			if (productRepository.countByMerchant(merchant) >= merchant.getMaxProducts()) {
				log.error("Merchant {} has reached the maximum number of products allowed: {}", merchant.getId(),
						merchant.getMaxProducts() + "For product: " + productDto.getName());
				throw new RuntimeException("Reached the maximum number of products allowed");
			}

			// ==========================================
			// 3. SLUG CHECK
			// ==========================================

			if (merchantSlug == null || !merchant.getStoreSlug().equalsIgnoreCase(merchantSlug.trim())) {
				log.error("Merchant slug mismatch. Expected: {}, Provided: {}", merchant.getStoreSlug(), merchantSlug);

				throw new IllegalArgumentException("Merchant slug does not match authenticated merchant");
			}

			// ==========================================
			// 4. PRICE VALIDATION
			// ==========================================

			if (productDto.getDiscountPrice() != null && productDto.getMrp() != null
					&& productDto.getMrp().compareTo(productDto.getDiscountPrice()) < 0) {

				throw new IllegalArgumentException("Discount price cannot be greater than MRP");
			}

			// ==========================================
			// 5. MAP PRODUCT
			// ==========================================

			Product product = modelMapper.map(productDto, Product.class);

			// ==========================================
			// 6. CATEGORY
			// ==========================================

			if (productDto.getCategoryId() != null) {
				log.info("Finding category for product " + productDto.getName() + " with ID: "
						+ productDto.getCategoryId());
				Category category = categoryRepo.findById(productDto.getCategoryId())
						.orElseThrow(() -> new RuntimeException("Category not found"));

				product.setCategoryId(category);
			}
//	        else {
//	            throw new IllegalArgumentException(
//	                "Category ID is required"
//	            );
//	        }
//	        product.setCategoryId(
//	            categoryRepo.findById(
//	                productDto.getCategoryId()
//	            ).orElseThrow(() ->
//	                new RuntimeException(
//	                    "Category not found"
//	                )
//	            )
//	        );

			// ==========================================
			// 7. MERCHANT
			// ==========================================

			product.setMerchant(merchant);

			product.setCreatedBy(user.getUsername());

			product.setCreatedAt(LocalDateTime.now());

			// ==========================================
			// 8. VARIANTS
			// ==========================================

			if (product.getVariants() != null) {
				log.info("Setting product reference in variants for product: " + product.getName());

				for (ProductVariant variant : product.getVariants()) {

					variant.setProduct(product);

					variant.setImages(new ArrayList<>());
				}
			}

			// Clear main product images collection just in case
			product.setImages(new ArrayList<>());

			// ==========================================
			// 9. SAVE PRODUCT FIRST
			// ==========================================

			Product savedProduct = productRepository.save(product);
			log.info("Product saved with ID: " + savedProduct.getId() + " and name: " + savedProduct.getName()
					+ " for merchant: " + merchant.getId() + " by user: " + user.getUsername()
					+ " proceed for image saving...");

			// ==========================================
			// 10. SAVE IMAGES
			// ==========================================

			if (imageFiles != null && imageFiles.length > 0) {
				log.info("Saving product images for product: " + savedProduct.getName() + " with ID: "
						+ savedProduct.getId());

				for (int i = 0; i < imageFiles.length; i++) {

					MultipartFile imageFile = imageFiles[i];

					if (imageFile == null || imageFile.isEmpty()) {
						continue;
					}

					// Save to local folder
					String imageUrl = imageStorageService.saveImage(savedProduct.getId(), imageFile);

					if (imageUrl == null || imageUrl.trim().isEmpty()) {
						log.warn("Skipping file save as generated image URL was empty for file: {}",
								imageFile.getOriginalFilename() + " for product: " + savedProduct.getName());
						continue;
					}

					// Create DB record
					ProductImage productImage = new ProductImage();

					productImage.setProduct(savedProduct);

					productImage.setImageUrl(imageUrl);

					productImage.setFileName(imageFile.getOriginalFilename());

					// FIRST IMAGE = PRIMARY
					productImage.setPrimaryImage(i == 0);

					productImage.setDisplayOrder(i);

					savedProduct.getImages().add(productImage);
					// Save image records
//		            productRepository.save(savedProduct);
					productImageRepository.save(productImage);
				}

			}

			// ========================================================
			// 10b. CORRECTED VARIANT IMAGE LOGIC
			// ========================================================
			if (savedProduct.getVariants() != null && request != null) {
				log.info("Saving variant images for product: " + savedProduct.getName() + " with ID: "
						+ savedProduct.getId());

				for (int v = 0; v < savedProduct.getVariants().size(); v++) {
					ProductVariant savedVariant = savedProduct.getVariants().get(v);

					// Matches keys like: "variantImages[0]", "variantImages[1]" sent from frontend
					String fileParamName = "variantImages[" + v + "]";
					java.util.List<MultipartFile> currentVariantFiles = request.getFiles(fileParamName);

					if (currentVariantFiles != null && !currentVariantFiles.isEmpty()) {
						for (int i = 0; i < currentVariantFiles.size(); i++) {
							MultipartFile variantFile = currentVariantFiles.get(i);
							if (variantFile == null || variantFile.isEmpty())
								continue;

							// Save using your new variant storage logic
							String variantImageUrl = imageStorageService.saveImage(savedVariant.getId(), variantFile);

							// 🚀 CRITICAL DEFENSE: Stop processing if variant storage returned null or
							// blank
							if (variantImageUrl == null || variantImageUrl.trim().isEmpty()) {
								log.warn("Skipping variant file save as generated URL was empty for file: {}",
										variantFile.getOriginalFilename() + " for variant ID: " + savedVariant.getId()
												+ " of product: " + savedProduct.getName());
								continue;
							}

							// Map directly to VariantImage Entity
							VariantImage variantImage = new VariantImage();
							variantImage.setProductVariant(savedVariant);
							variantImage.setImageUrl(variantImageUrl);
							variantImage.setFileName(variantFile.getOriginalFilename());
							variantImage.setPrimaryImage(i == 0); // First image for this variant is primary
							variantImage.setDisplayOrder(i);

							savedVariant.getImages().add(variantImage);

//	                         🚀 SAVE VIA REPOSITORY TO AVOID HIBERNATE CASCADE MIX-UPS
							variantImageRepository.save(variantImage);
						}
					}
				}
			}

			// Final comprehensive sync to cascade save all product & nested variant image
			// records
			Product fullySavedProduct = productRepository.save(savedProduct);
			log.info("Final save completed for product: " + fullySavedProduct.getName() + " with ID: "
					+ fullySavedProduct.getId() + " and total images: "
					+ (fullySavedProduct.getImages() != null ? fullySavedProduct.getImages().size() : 0)
					+ " and total variants: "
					+ (fullySavedProduct.getVariants() != null ? fullySavedProduct.getVariants().size() : 0));

			// ==========================================
			// 11. RESPONSE
			// ==========================================

			return modelMapper.map(
//	                savedProduct,
					fullySavedProduct, ProductDto.class);

		} catch (Exception e) {

			log.error("Error creating product", e);

			throw new RuntimeException("Failed to create product", e);

//	        return new ProductDto(e.getMessage());
		}
	}

	public Product getProductById(Long productId) {
		return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
	}

	public ProductDto getProductDtoById(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		ProductDto dto = modelMapper.map(product, ProductDto.class);

		// Convert BLOB -> Base64 manually (ModelMapper cannot do this)
		if (product.getImageData() != null) {
			dto.setImageBase64(Base64.getEncoder().encodeToString(product.getImageData()));
		}

		return dto;
	}

	public Product getProductBySku(String sku) {
		return productRepository.findBySku(sku)
				.orElseThrow(() -> new RuntimeException("Product with SKU " + sku + " not found"));
	}

	public List<ProductDto> getAllProducts() {

		Merchant merchant = authenticationService.getCurrentMerchant(); // Adm9n user is authenticated

		log.info(
				"Fetching all products for merchant " + merchant.getBusinessName() + " ," + merchant.getMerchantCode());
		List<Product> products = productRepository.findAllByMerchant(merchant);
//		return products.stream().map(product -> modelMapper.map(product, ProductDto.class))
//				.collect(Collectors.toList());

		return products.stream().map(ProductDto::new).collect(Collectors.toList());

	}

	public List<ProductDto> getAllActiveProducts(String slug) {
		log.info("Fetching all active products for merchant slug: " + slug);
		Merchant merchant = merchantService.getMerchantBySlug(slug); // Verify merchant exists

		return productRepository.findAllByIsActiveAndMerchant("Y", merchant).stream().map(ProductDto::new)
				.collect(Collectors.toList());
	}

	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	public void deleteProduct(Long productId) {
		productRepository.deleteById(productId);
	}

	public List<ProductDto> getProductsByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category not found"));

		List<Product> products = productRepository.findByCategoryId(category);

		return products.stream().map(product -> modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
	}

	// commented for change logic -08oct26
//	public ProductDto updateProduct(Long productId, ProductDto updatedProductDto, MultipartFile imageFile) throws IOException {
//		log.info("In Product service for Updating product : "+updatedProductDto);
//		Product existingProduct = getProductById(productId);
//		
//
//		existingProduct.setName(updatedProductDto.getName());
//		existingProduct.setDescription(updatedProductDto.getDescription());
//		existingProduct.setPrice(updatedProductDto.getPrice());
//		existingProduct.setMrp(updatedProductDto.getMrp());
//		existingProduct.setDiscountPrice(updatedProductDto.getDiscountPrice());
//		existingProduct.setBrand(updatedProductDto.getBrand());
//		existingProduct.setQuantityInStock(updatedProductDto.getQuantityInStock());
//		existingProduct.setWeight(updatedProductDto.getWeight());
//		existingProduct.setDimensions(updatedProductDto.getDimensions());
////		existingProduct.setImageUrl(updatedProductDto.getImageUrl());
//		existingProduct.setManufacturer(updatedProductDto.getManufacturer());
//		existingProduct.setRating(updatedProductDto.getRating());
//		existingProduct.setIsActive(updatedProductDto.getIsActive());
//		
//		//existingProduct.setUpdatedAt(updatedProductDto.getUpdatedAt());
//if(updatedProductDto.getCategoryId()!=null) {
//		 Category category = categoryRepository.findById(
//		            updatedProductDto.getCategoryId()
//		    ).orElseThrow(() -> new RuntimeException("Category not found"));
//
//		    existingProduct.setCategoryId(category);
//}
//
////handle variants correctly
//// =========================
//
//if (updatedProductDto.getVariants() != null) {
//log.info("Updating variants for product ID: " + productId);
//    // Remove existing variants.
//    // Requires orphanRemoval = true on Product -> Variant mapping.
//    existingProduct.getVariants().clear();
//
//    for (VariantDTO variantDto : updatedProductDto.getVariants()) {
//
//        ProductVariant variant = new ProductVariant();
//
//        // variant fields
//        variant.setColor(variantDto.getColor());
//        variant.setSize(variantDto.getSize());
//        variant.setPrice(variantDto.getPrice());
//        variant.setStock(variantDto.getStock());
//        variant.setSku(variantDto.getSku());
//        variant.setIsActive(variantDto.getIsActive());
//
//        // Set parent
//        variant.setProduct(existingProduct);
//
//        // Add to parent's collection
//        existingProduct.getVariants().add(variant);
//    }
//}
//		   // Update BLOB image
//	    if (imageFile != null && !imageFile.isEmpty()) {
//	    	existingProduct.setImageData(imageFile.getBytes());
//	    }
//
//	    Product saved = productRepository.save(existingProduct);
//
//	    // return DTO with base64
//	    return new ProductDto(saved);
//	}

	// update product with vaiant n image new logic - 08oct26
	@Transactional
	public ProductDto updateProduct(Long productId, ProductDto productDto, MultipartFile[] imageFiles,
			org.springframework.web.multipart.MultipartHttpServletRequest request, String merchantSlug)
			throws IOException {

		log.info("Updating product ID: {}, DTO: {}", productId, productDto.toString());

		try {
			// ==========================================
			// 1. CURRENT USER & VALIDATIONS
			// ==========================================
			User user = authenticationService.getCurrentUser();
			if (user == null)
				throw new RuntimeException("Authenticated user not found");

			Merchant merchant = user.getMerchant();
			if (merchant == null)
				throw new RuntimeException("User is not associated with merchant");

			if (merchantSlug == null || !merchant.getStoreSlug().equalsIgnoreCase(merchantSlug.trim())) {
				throw new IllegalArgumentException("Merchant slug does not match authenticated merchant");
			}

			if (productDto.getDiscountPrice() != null && productDto.getMrp() != null
					&& productDto.getMrp().compareTo(productDto.getDiscountPrice()) < 0) {
				throw new IllegalArgumentException("Discount price cannot be greater than MRP");
			}

			Product existingProduct = getProductById(productId);

			// ==========================================
			// 2. UPDATE CORE PRIMITIVE PROPERTIES
			// ==========================================
			existingProduct.setName(productDto.getName());
			existingProduct.setDescription(productDto.getDescription());
			existingProduct.setPrice(productDto.getPrice());
			existingProduct.setMrp(productDto.getMrp());
			existingProduct.setDiscountPrice(productDto.getDiscountPrice());
			existingProduct.setBrand(productDto.getBrand());
			existingProduct.setQuantityInStock(productDto.getQuantityInStock());
			existingProduct.setWeight(productDto.getWeight());
			existingProduct.setDimensions(productDto.getDimensions());
			existingProduct.setManufacturer(productDto.getManufacturer());
			existingProduct.setRating(productDto.getRating());
			existingProduct.setIsActive(productDto.getIsActive());
//			existingProduct.setUpdatedBy(user.getUsername());
			existingProduct.setUpdatedAt(LocalDateTime.now());

			if (productDto.getCategoryId() != null) {
				Category category = categoryRepo.findById(productDto.getCategoryId())
						.orElseThrow(() -> new RuntimeException("Category not found"));
				existingProduct.setCategoryId(category);
			}

			// ==========================================
			// 3. SYNCHRONIZE MAIN PRODUCT IMAGES (PRESERVE OLD) 📸
			// ==========================================
			// Identify which old image IDs the frontend wants to keep
			List<Long> incomingMainImageIds = new ArrayList<>();
			if (productDto.getImages() != null) {
				productDto.getImages().forEach(img -> {
					if (img.getId() != null)
						incomingMainImageIds.add(img.getId());
				});
			}

			// Delete from DB only the images that are NOT in the incoming DTO list
			if (existingProduct.getImages() != null) {
				List<ProductImage> imagesToRemove = existingProduct.getImages().stream()
						.filter(img -> !incomingMainImageIds.contains(img.getId())).collect(Collectors.toList());

				for (ProductImage img : imagesToRemove) {
					existingProduct.getImages().remove(img);
					productImageRepository.delete(img);
				}
			}

			// ==========================================
			// 4. SYNCHRONIZE VARIANTS AND VARIANT IMAGES 🚀
			// ==========================================
			List<Long> incomingVariantIds = new ArrayList<>();
			if (productDto.getVariants() != null) {
				productDto.getVariants().forEach(v -> {
					if (v.getId() != null)
						incomingVariantIds.add(v.getId());
				});
			}

			// A. Remove variants from DB that were removed on the frontend layout
			if (existingProduct.getVariants() != null) {
				List<ProductVariant> variantsToRemove = existingProduct.getVariants().stream()
						.filter(v -> !incomingVariantIds.contains(v.getId())).collect(Collectors.toList());

				for (ProductVariant v : variantsToRemove) {
					// Delete child variant images first to clear database constraints
					variantImageRepository.deleteByProductVariantId(v.getId());
					existingProduct.getVariants().remove(v);
					productVariantRepository.delete(v);
				}
				variantImageRepository.flush();
				productVariantRepository.flush();
			}

			// B. Update Existing Variants / Add Brand New Variants
			List<ProductVariant> currentVariantsInOrder = new ArrayList<>();

			if (productDto.getVariants() != null) {
				for (VariantDTO vDto : productDto.getVariants()) {
					ProductVariant variant;

					if (vDto.getId() != null) {
						// Update existing row
						variant = productVariantRepository.findById(vDto.getId())
								.orElseThrow(() -> new RuntimeException("Variant not found"));
					} else {
						// Create brand new row configuration
						variant = new ProductVariant();
						variant.setImages(new ArrayList<>());
					}

					variant.setSize(vDto.getSize());
					variant.setColor(vDto.getColor());
					variant.setPrice(vDto.getPrice());
					variant.setStock(vDto.getStock());
					variant.setSku(vDto.getSku());
					variant.setIsActive(vDto.getIsActive());
					variant.setProduct(existingProduct);

					// Synchronize existing inner variant images if updating an old variant
					List<Long> incomingVarImgIds = new ArrayList<>();
					if (vDto.getImages() != null) {
						vDto.getImages().forEach(img -> {
							if (img.getId() != null)
								incomingVarImgIds.add(img.getId());
						});
					}

					if (variant.getImages() != null) {
						List<VariantImage> varImgsToRemove = variant.getImages().stream()
								.filter(img -> !incomingVarImgIds.contains(img.getId())).collect(Collectors.toList());
						for (VariantImage vi : varImgsToRemove) {
							variant.getImages().remove(vi);
							variantImageRepository.delete(vi);
						}
					}

					ProductVariant savedVariant = productVariantRepository.saveAndFlush(variant);
					currentVariantsInOrder.add(savedVariant);
				}
			}

			// Synchronize base product entity state boundaries
			existingProduct.setId(productId);
			Product savedProduct = productRepository.saveAndFlush(existingProduct);

			// ==========================================
			// 5. APPEND NEW MAIN IMAGES TO THE EXISTING LIST
			// ==========================================
			int currentMainImagesCount = savedProduct.getImages() != null ? savedProduct.getImages().size() : 0;

			if (imageFiles != null && imageFiles.length > 0) {
				for (int i = 0; i < imageFiles.length; i++) {
					MultipartFile imageFile = imageFiles[i];
					if (imageFile == null || imageFile.isEmpty())
						continue;

					String imageUrl = imageStorageService.saveImage(savedProduct.getId(), imageFile);
					if (imageUrl == null || imageUrl.trim().isEmpty())
						continue;

					ProductImage productImage = new ProductImage();
					productImage.setProduct(savedProduct);
					productImage.setImageUrl(imageUrl);
					productImage.setFileName(imageFile.getOriginalFilename());
					productImage.setPrimaryImage(currentMainImagesCount == 0 && i == 0);
					productImage.setDisplayOrder(currentMainImagesCount + i);

					productImageRepository.save(productImage);
				}
			}

			// ==========================================
			// 6. APPEND NEW VARIANT IMAGES AT CORRECT POSITION MAPS
			// ==========================================
			if (!currentVariantsInOrder.isEmpty() && request != null) {
				for (int v = 0; v < currentVariantsInOrder.size(); v++) {
					ProductVariant savedVariant = currentVariantsInOrder.get(v);

					String fileParamName = "variantImages[" + v + "]";
					List<MultipartFile> currentVariantFiles = request.getFiles(fileParamName);

					int currentVarImgCount = savedVariant.getImages() != null ? savedVariant.getImages().size() : 0;

					if (currentVariantFiles != null && !currentVariantFiles.isEmpty()) {
						for (int i = 0; i < currentVariantFiles.size(); i++) {
							MultipartFile variantFile = currentVariantFiles.get(i);
							if (variantFile == null || variantFile.isEmpty())
								continue;

							String variantImageUrl = imageStorageService.saveImage(savedVariant.getId(),
									variantFile);
							if (variantImageUrl == null || variantImageUrl.trim().isEmpty())
								continue;

							VariantImage variantImage = new VariantImage();
							variantImage.setProductVariant(savedVariant);
							variantImage.setImageUrl(variantImageUrl);
							variantImage.setFileName(variantFile.getOriginalFilename());
							variantImage.setPrimaryImage(currentVarImgCount == 0 && i == 0);
							variantImage.setDisplayOrder(currentVarImgCount + i);
							variantImageRepository.save(variantImage);
						}
					}
				}
			}
// ==========================================
// 7. RETURN PERSISTED GRAPH DTO
// ==========================================
			Product freshPersistedProduct = productRepository.findById(savedProduct.getId())
					.orElseThrow(() -> new RuntimeException("Product not found"));
			return modelMapper.map(freshPersistedProduct, ProductDto.class);
		} catch (Exception e) {
			log.error("Error updating product structure for ID: " + productId, e);
			throw new RuntimeException("Failed to update product", e);
		}
	}

// end of update


	// GET PRODUCT WITH REVIEWS

	public ProductDto getProductWithReviews(Long productId) {
		log.info("Fetching product {} with reviews", productId);
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		ProductDto dto = new ProductDto(product);
		return dto;
	}

	// ====================================
	// GET PRODUCT WITH AVERAGE RATING
	// ====================================
	public ProductDto getProductWithRating(Long productId) {
		log.info("Fetching product {} with rating", productId);
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		// Returns product with averageRating and totalReviews fields
		ProductDto dto = new ProductDto(product);
		return dto;
	}
}