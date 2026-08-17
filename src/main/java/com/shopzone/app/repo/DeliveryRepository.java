package com.shopzone.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopzone.app.entity.Delivery;
import com.shopzone.app.entity.Order;
import com.shopzone.app.entity.User;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByDeliveryBoyId(Long deliveryBoyId);

	List<Delivery> findByDeliveryBoyId(User deliveryBoy);

//	Optional<Delivery> findByDeliveryBoy(User deliveryBoy);

	List<Delivery> findAllByDeliveryBoy(User deliveryBoy);
}

