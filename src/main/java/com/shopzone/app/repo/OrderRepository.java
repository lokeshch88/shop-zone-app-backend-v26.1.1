package com.shopzone.app.repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopzone.app.entity.Merchant;
import com.shopzone.app.entity.Order;
import com.shopzone.app.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findByUserId(Long userId);

	Optional<Order> findByOrderId(String orderId);

	List<Order> findAllByMerchant(Merchant merchant);

}
