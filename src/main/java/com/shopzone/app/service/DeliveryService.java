package com.shopzone.app.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopzone.app.dto.DeliveryResponseDto;
import com.shopzone.app.dto.OrderItemDto;
import com.shopzone.app.dto.OrderResponse;
import com.shopzone.app.entity.Delivery;
import com.shopzone.app.entity.Order;
import com.shopzone.app.entity.OrderStatus;
import com.shopzone.app.entity.User;
import com.shopzone.app.repo.AddressRepository;
import com.shopzone.app.repo.DeliveryRepository;
import com.shopzone.app.repo.OrderRepository;
import com.shopzone.app.repo.UserRepo;

@Service
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;
	private final OrderRepository orderRepository;
	private final UserRepo userRepo;

	public DeliveryService(DeliveryRepository deliveryRepository, OrderRepository orderRepository,
			UserRepo userRepo) {
		this.deliveryRepository = deliveryRepository;
		this.orderRepository = orderRepository;
		this.userRepo = userRepo;
	}
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AddressRepository addressRepository;

	// ADMIN: Assign delivery boy to order
	public Delivery assignDeliveryBoy(String orderId, Long deliveryBoyId) {

		Order order = orderRepository.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		User deliveryBoy = userRepo.findById(deliveryBoyId)
				.orElseThrow(() -> new RuntimeException("Delivery boy not found"));
		Delivery delivery = new Delivery();
		delivery.setDeliveryBoy(deliveryBoy);
		delivery.setOrder(order);
		delivery.setStatus(OrderStatus.ASSIGNED);
		
		order.setDeliveryId(delivery);
		order.setStatus(OrderStatus.ASSIGNED);

		return deliveryRepository.save(delivery);
	}

	// Get my assigned deliveries
//	public List<Delivery> getMyDeliveries() {
//
//		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		return deliveryRepository.findByDeliveryBoyId(currentUser.getId());
//	}

	// Update delivery status
	public DeliveryResponseDto updateDeliveryStatus(Long deliveryId, OrderStatus status, String orderId) {

//		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Delivery delivery = deliveryRepository.findById(deliveryId)
				.orElseThrow(() -> new RuntimeException("Delivery not found"));

		Order order =orderRepository.findByOrderId(orderId)
		.orElseThrow(()-> new RuntimeException("Order not found"));
//		if (delivery.getDeliveryBoy() == null || !delivery.getDeliveryBoy().getId().equals(currentUser.getId())) {
//			throw new RuntimeException("You are not assigned to this delivery");
//		}

		delivery.setStatus(status);
		Delivery delv= deliveryRepository.save(delivery);
		order.setStatus(status);
		orderRepository.save(order);
		 return modelMapper.map(delv, DeliveryResponseDto.class);
	}

	public List<DeliveryResponseDto> getMyDeliveries(String username) {

	    User deliveryBoy = userRepo.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    return ((Stream<DeliveryResponseDto>) deliveryRepository.findAllByDeliveryBoy(deliveryBoy)
	            .stream()
	            .map(delivery -> {
	                DeliveryResponseDto dto = new DeliveryResponseDto();
	                dto.setDeliveryId(delivery.getId());      // THIS IS IMPORTANT
	                dto.setOrderId(delivery.getOrder().getOrderId());
	                dto.setStatus(delivery.getStatus());
	                dto.setShippedAt(delivery.getShippedAt());
	                dto.setDeliveredAt(delivery.getDeliveredAt());

	                // Map order details
	                Order order = delivery.getOrder();
	                OrderResponse orderResponse = new OrderResponse();
	                orderResponse.setId(String.valueOf(order.getOrderId()));
	                orderResponse.setUserId(order.getUser().getId());
	                orderResponse.setStatus(order.getStatus());
	                orderResponse.setTotalAmount(order.getTotalAmount());
	                orderResponse.setCreatedAt(order.getCreatedAt());

	                // Map order items
	                List<OrderItemDto> items = ((Stream<OrderItemDto>) order.getItems().stream().map(item -> {
	                    OrderItemDto itemDto = new OrderItemDto();
	                    itemDto.setProductId(item.getProduct().getId());
	                    itemDto.setPrice(item.getPrice());
	                    itemDto.setQuantity(item.getQuantity());
	                    itemDto.setColor(item.getColor());
	                    itemDto.setSize(item.getSize());
	                    return itemDto;
	                })) .collect(Collectors.toList());
	                orderResponse.setItems(items);

	                // Map address
//	                if (order.getAddressId() != null) {
//	                    Address address = addressRepository.findById(order.getAddressId()).orElse(null);
//	                    if (address != null) {
//	                        AddressDto addrDto = modelMapper.map(address, AddressDto.class);
//	                        orderResponse.setDeliveryAddress(addrDto);
//	                    }
//	                }

	                dto.setOrderResponse(orderResponse);
	                return dto;
	            }))
	    		 .collect(Collectors.toList());
	}



	
	

}
