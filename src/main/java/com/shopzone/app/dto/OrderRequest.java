package com.shopzone.app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderRequest {
	
	
    private LocalDateTime orderDateTime;
 
        private List<VariantDTO> products;
        private Double totalAmount;
        private List<OrderItemDto> items;
        private Long addressId;
        
        
        private String paymentMethod;

        private String paymentStatus;
        private String paymentId;
        private String paymentSignature;
        private String currency;
        private String transactionId;
        private String paymentGateway;
        private String paymentError;
        private LocalDateTime paymentDateTime;
        
        //checkout deatisl
        private String couponCodeApplied;
        private Double couponDiscount;
        private Double shippingCost;
        private Double taxAmount;
        private String estimatedDeliveryDate;
        private Integer totalItems;
        
        
        
        public OrderRequest() {}

      

        public String getPaymentMethod() {
			return paymentMethod;
		}



		public void setPaymentMethod(String paymentMethod) {
			this.paymentMethod = paymentMethod;
		}



		public String getPaymentStatus() {
			return paymentStatus;
		}



		public void setPaymentStatus(String paymentStatus) {
			this.paymentStatus = paymentStatus;
		}



		public String getPaymentId() {
			return paymentId;
		}



		public void setPaymentId(String paymentId) {
			this.paymentId = paymentId;
		}



		public String getPaymentSignature() {
			return paymentSignature;
		}



		public void setPaymentSignature(String paymentSignature) {
			this.paymentSignature = paymentSignature;
		}



		public String getCurrency() {
			return currency;
		}



		public void setCurrency(String currency) {
			this.currency = currency;
		}



		public String getTransactionId() {
			return transactionId;
		}



		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}



		public String getPaymentGateway() {
			return paymentGateway;
		}



		public void setPaymentGateway(String paymentGateway) {
			this.paymentGateway = paymentGateway;
		}



		public String getPaymentError() {
			return paymentError;
		}



		public void setPaymentError(String paymentError) {
			this.paymentError = paymentError;
		}



		public LocalDateTime getPaymentDateTime() {
			return paymentDateTime;
		}



		public void setPaymentDateTime(LocalDateTime paymentDateTime) {
			this.paymentDateTime = paymentDateTime;
		}



		public String getCouponCodeApplied() {
			return couponCodeApplied;
		}



		public void setCouponCodeApplied(String couponCodeApplied) {
			this.couponCodeApplied = couponCodeApplied;
		}



		public Double getCouponDiscount() {
			return couponDiscount;
		}



		public void setCouponDiscount(Double couponDiscount) {
			this.couponDiscount = couponDiscount;
		}



		public Double getShippingCost() {
			return shippingCost;
		}



		public void setShippingCost(Double shippingCost) {
			this.shippingCost = shippingCost;
		}



		public Double getTaxAmount() {
			return taxAmount;
		}



		public void setTaxAmount(Double taxAmount) {
			this.taxAmount = taxAmount;
		}



		public String getEstimatedDeliveryDate() {
			return estimatedDeliveryDate;
		}



		public void setEstimatedDeliveryDate(String estimatedDeliveryDate) {
			this.estimatedDeliveryDate = estimatedDeliveryDate;
		}



		public Integer getTotalItems() {
			return totalItems;
		}



		public void setTotalItems(Integer totalItems) {
			this.totalItems = totalItems;
		}



		public Double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
        }

		public LocalDateTime getOrderDateTime() {
			return orderDateTime;
		}

		public void setOrderDateTime(LocalDateTime orderDateTime) {
			this.orderDateTime = orderDateTime;
		}



		public List<VariantDTO> getProducts() {
			return products;
		}



		public void setProducts(List<VariantDTO> products) {
			this.products = products;
		}



		public List<OrderItemDto> getItems() {
			return items;
		}



		public void setItems(List<OrderItemDto> items) {
			this.items = items;
		}



		public Long getAddressId() {
			return addressId;
		}



		public void setAddressId(Long addressId) {
			this.addressId = addressId;
		}
   

  
	
	
    
    
}

