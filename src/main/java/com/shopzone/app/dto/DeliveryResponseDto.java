package com.shopzone.app.dto;

import com.shopzone.app.entity.OrderStatus;
import java.time.LocalDateTime;

public class DeliveryResponseDto {

    private Long deliveryId;
    private String orderId;

    private String customerName;
    private String phone;
    private String city;
    private String state;

    private OrderStatus status;

    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    
    private OrderResponse orderResponse;
    
	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public LocalDateTime getShippedAt() {
		return shippedAt;
	}
	public void setShippedAt(LocalDateTime shippedAt) {
		this.shippedAt = shippedAt;
	}
	public LocalDateTime getDeliveredAt() {
		return deliveredAt;
	}
	public void setDeliveredAt(LocalDateTime deliveredAt) {
		this.deliveredAt = deliveredAt;
	}
	public OrderResponse getOrderResponse() {
		return orderResponse;
	}
	public void setOrderResponse(OrderResponse orderResponse) {
		this.orderResponse = orderResponse;
	}

    
}
