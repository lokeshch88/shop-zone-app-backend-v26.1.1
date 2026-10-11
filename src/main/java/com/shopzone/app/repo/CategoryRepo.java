package com.shopzone.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopzone.app.entity.Category;
import com.shopzone.app.entity.Merchant;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

	List<Category> findAllByMerchant(Merchant merchant);

}
