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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private ProductService productService;

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	// Create a new product
	@PostMapping(
		    value = "/create",
		    consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
		)
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<ProductDto> createProduct(
		        @RequestPart("product") ProductDto product,
		        @RequestPart(value = "image", required = false) MultipartFile imageFile
		) {
		    log.info("In create new product method");

		    ProductDto createdProduct = productService.createProduct(product, imageFile);
		    log.info("Product created with id " + createdProduct.getId());
		    return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
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
	public ResponseEntity<ResponseDto<ProductDto>> getAllActiveProducts() {
		List<ProductDto> list = productService.getAllActiveProducts();

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
	// Update an existing product
//	@PutMapping("/{productId}")
////   @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
//	@PreAuthorize("hasRole('ADMIN')")
//	public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
//		log.info("In product update ");
//		try {
//			Product updated = productService.updateProduct(productId, updatedProduct);
//			log.info("Product updated successfully ");
//			return new ResponseEntity<>(updated, HttpStatus.OK);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
//	}
 // Update an existing product
    @PutMapping(value = "/{productId}", consumes = { "multipart/form-data" })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long productId,
            @RequestPart("product") ProductDto updatedProductDto,
         //   @RequestBody(required = false) ProductDto updatedProductDto, // fallback
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        log.info("In product update");

        try {
            ProductDto updatedProduct = productService.updateProduct(productId, updatedProductDto, imageFile);
            log.info("Product updated successfully");

            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error updating product: {}", e.getMessage());
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

		return ResponseEntity.ok(map);
	}

}
