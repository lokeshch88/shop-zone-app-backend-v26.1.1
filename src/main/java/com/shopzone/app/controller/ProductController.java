package com.shopzone.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.shopzone.app.dto.ProductDto;
import com.shopzone.app.dto.ResponseDto;
import com.shopzone.app.entity.Category;
import com.shopzone.app.entity.Product;
import com.shopzone.app.service.ProductService;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private ProductService productService;

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

//	// Create a new product
//	@PostMapping(
//		    value = "/create",
//		    consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
//		)
//		@PreAuthorize("hasRole('ADMIN')")
//		public ResponseEntity<ProductDto> createProduct(
//		        @RequestPart("product") ProductDto product,
//		        @RequestPart(value = "images", required = false) MultipartFile[] imageFiles,
//		        @RequestHeader("X-Merchant-Slug") String merchantSlug
//		) {
//		    log.info("In create new product method for product: {}", product.getName());
//
//		    ProductDto createdProduct = productService.createProduct(product, imageFiles, merchantSlug);
//		    
//		    if (createdProduct != null) {
//		    	  log.info("Product created name: "+ createdProduct.getName()+" with id: " + createdProduct.getId());
//		        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
//		    } else {
//		    					log.error("Failed to create product: {}", product.getName());
//		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		    }
//		  
//		//	    return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
//		}

	// Create a new product
	@PostMapping(
	        value = "/create",
	        consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
	)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductDto> createProduct(
	        @RequestPart("product") ProductDto product,
	        @RequestPart(value = "images", required = false) MultipartFile[] imageFiles,
//	        @RequestPart(value = "variantImages", required = false) MultipartFile[] variantImageFiles, // variants
	        org.springframework.web.multipart.MultipartHttpServletRequest request ,
	        @RequestHeader("X-Merchant-Slug") String merchantSlug
	) {
	    log.info("In create new product method for product: {}", product.getName());

	    // ==========================================
	    // IMAGE COUNT VALIDATION (MAX LIMIT CHECK)
	    // ==========================================
	    final int MAX_PRODUCT_IMAGES = 1; //  threshold
	    final int MAX_VARIANT_IMAGES = 3; // threshold per variant/total

	    if (imageFiles != null && imageFiles.length > MAX_PRODUCT_IMAGES) {
	        log.warn("Product image limit exceeded. Got: {}", imageFiles.length);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .header("X-Error-Reason", "Maximum " + MAX_PRODUCT_IMAGES + " main images allowed")
	                .build();
	    }

//	    if (request != null && request.() > MAX_VARIANT_IMAGES) {
//	        log.warn("Variant image limit exceeded. Got: {}",  request.getContentLength() );
//	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//	                .header("X-Error-Reason", "Maximum " + MAX_VARIANT_IMAGES + " variant images allowed")
//	                .build();
//	    }

	    // Pass the variant image files into the updated service layer method
	    ProductDto createdProduct = productService.createProduct(product, imageFiles, request, merchantSlug);
	    
	    if (createdProduct != null) {
	        log.info("Product created name: {} with id: {}", createdProduct.getName(), createdProduct.getId());
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	    } else {
	        log.error("Failed to create product: {}", product.getName());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	// Get product by ID
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
		try {
			ProductDto product = productService.getProductDtoById(productId);
			return new ResponseEntity<>(product, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

//
//    // Get product by SKU
//    @GetMapping("/sku/{sku}")
//    public ResponseEntity<Product> getProductBySku(@PathVariable String sku) {
//        try {
//            Product product = productService.getProductBySku(sku);
//            return new ResponseEntity<>(product, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
	// Get all products
	@GetMapping("all")
	 @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseDto<ProductDto>> getAllProducts() {
		List<ProductDto> list = productService.getAllProducts();

		ResponseDto<ProductDto> resp = new ResponseDto<ProductDto>();
		if (list.isEmpty()) {
			resp.setStatus(1);
			resp.setMessage("No Product founds");
		}
		resp.setStatus(0);
		resp.setResult(list);
		resp.setMessage("Products fetched successfully");
		return ResponseEntity.ok(resp);
	}
	
	@GetMapping("all-active")
	public ResponseEntity<ResponseDto<ProductDto>> getAllActiveProducts(@RequestHeader(value = "X-Merchant-Slug", required = true) String slug) {
		List<ProductDto> list = productService.getAllActiveProducts(slug);

		ResponseDto<ProductDto> resp = new ResponseDto<ProductDto>();
		if (list.isEmpty()) {
			resp.setStatus(1);
			resp.setMessage("No Product founds");
		}
		resp.setStatus(0);
		resp.setResult(list);
		resp.setMessage("Products fetched successfully");
		return ResponseEntity.ok(resp);
	}


    // Get products by category
    @GetMapping("/category/{category}")
    public List<ProductDto> getProductsByCategory(@PathVariable Long category) {
        return productService.getProductsByCategory(category);
    }
//
//    // Get products by brand
//    @GetMapping("/brand/{brand}")
//    public List<Product> getProductsByBrand(@PathVariable String brand) {
//        return productService.getProductsByBrand(brand);
//    }
//

 // Update an existing product -commented to change logic for variant and images -08oct26
//    @PutMapping(value = "/{productId}", consumes = { "multipart/form-data" })
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<ProductDto> updateProduct(
//            @PathVariable Long productId,
//            @RequestPart("product") ProductDto updatedProductDto,
//         //   @RequestBody(required = false) ProductDto updatedProductDto, // fallback
//            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
//
//        log.info("In product update for product ID: " + productId);
//
//        try {
//            ProductDto updatedProduct = productService.updateProduct(productId, updatedProductDto, imageFile);
//            log.info("Product updated successfully");
//
//            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
//
//        } catch (Exception e) {
//            log.error("Error updating product: {}", e.getStackTrace());
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }

    
 // Update an existing product
    @PutMapping(value = "/{productId}", consumes = { "multipart/form-data" })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long productId,
            @RequestPart("product") ProductDto updatedProductDto,
            @RequestPart(value = "image", required = false) MultipartFile[] imageFiles, // 👈 Kept your name but converted to array for multiple images
            org.springframework.web.multipart.MultipartHttpServletRequest request, // 👈 Added for dynamic variant images
            @RequestHeader("X-Merchant-Slug") String merchantSlug
    ) {
        log.info("In product update for product ID: " + productId);

        try {
            // Pass the request object down to the service layer
            ProductDto updatedProduct = productService.updateProduct(productId, updatedProductDto, imageFiles, request,merchantSlug );
            log.info("Product updated successfully");
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating product", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


//
	// Delete product
	@DeleteMapping("/{productId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long productId) {
		try {
			productService.deleteProduct(productId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/productvariant")
	public ResponseEntity<Map<String, List<String>>> getProductsVariants() {
		Map<String, List<String>> map = new HashMap<>();

		List<String> colorList = Arrays.asList("Red", "Blue", "Green", "Black", "White");
		List<String> sizeList = Arrays.asList("S", "M", "L", "XL", "XXL");

		map.put("colors", colorList);
		map.put("sizes", sizeList);
		map.put("materials", Arrays.asList("Cotton", "Polyester", "Wool", "Silk"));
		map.put("imageUploadAllowedVariant", Arrays.asList("colors","materials"));

		return ResponseEntity.ok(map);
	}

	// ====================================
	// GET PRODUCT WITH REVIEWS
	// ====================================
	@GetMapping("/{productId}/with-reviews")
	public ResponseEntity<?> getProductWithReviews(@PathVariable Long productId) {
		log.info("GET /products/{}/with-reviews - Fetching product with reviews", productId);

		try {
			ProductDto product = productService.getProductWithReviews(productId);
			return ResponseEntity.ok(product);

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Collections.singletonMap("error", "Product not found"));
		}
	}

	// ====================================
	// GET PRODUCT WITH RATING
	// ====================================
	@GetMapping("/{productId}/with-rating")
	public ResponseEntity<?> getProductWithRating(@PathVariable Long productId) {
		log.info("GET /products/{}/with-rating - Fetching product with rating", productId);

		try {
			ProductDto product = productService.getProductWithRating(productId);
			return ResponseEntity.ok(product);

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Collections.singletonMap("error", "Product not found"));
		}
	}

}

