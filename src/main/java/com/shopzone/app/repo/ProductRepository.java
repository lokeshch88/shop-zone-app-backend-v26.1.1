package com.shopzone.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopzone.app.entity.Category;
import com.shopzone.app.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    // Custom query to find products by category
//    List<Product> findByCategory(String category);

    // Custom query to find products by brand
    List<Product> findByBrand(String brand);

	List<Product> findByCategoryId(Category category);

	List<Product> findAllByIsActive(String string);
}

