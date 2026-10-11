package com.shopzone.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopzone.app.entity.ProductVariant;
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

	void deleteByProductId(Long productId);

}
