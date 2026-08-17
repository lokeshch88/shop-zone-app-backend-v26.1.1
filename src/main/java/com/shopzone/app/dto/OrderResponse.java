package com.shopzone.app.dto;

import com.shopzone.app.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private String id;
    private Long userId;
    private OrderStatus status;
    private List<OrderItemDto> items;
    private PaymentDto payemntDetails;
    private Double totalAmount;
    private DeliveryResponseDto deliveryDetailsDto;

	private LocalDateTime createdAt;
	private AddressDto deliveryAddress;
  	 
    public OrderResponse() {}

    public OrderResponse(String orderId, Long userId, OrderStatus status, Double totalAmount) {
        this.id = orderId;
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return id;
    }

    public void setOrderId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

 

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

	public List<OrderItemDto> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDto> items) {
		this.items = items;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PaymentDto getPayemntDetails() {
		return payemntDetails;
	}

	public void setPayemntDetails(PaymentDto payemntDetails) {
		this.payemntDetails = payemntDetails;
	}

	public DeliveryResponseDto getDeliveryDetailsDto() {
		return deliveryDetailsDto;
	}

	public void setDeliveryDetailsDto(DeliveryResponseDto deliveryDetailsDto) {
		this.deliveryDetailsDto = deliveryDetailsDto;
	}

	public AddressDto getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(AddressDto deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	
	
}
