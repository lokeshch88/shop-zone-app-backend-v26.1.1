package com.shopzone.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopzone.app.entity.VariantImage;

public interface VariantImageRepository extends JpaRepository<VariantImage, Long> {

	void deleteByProductVariantId(Long id);

}
