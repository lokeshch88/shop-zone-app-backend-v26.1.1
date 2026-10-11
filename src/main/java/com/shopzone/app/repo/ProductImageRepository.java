package com.shopzone.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopzone.app.entity.Order;
import com.shopzone.app.entity.ProductImage;

public interface ProductImageRepository  extends JpaRepository<ProductImage, Long>{

	void deleteByProductId(Long productId);

}
