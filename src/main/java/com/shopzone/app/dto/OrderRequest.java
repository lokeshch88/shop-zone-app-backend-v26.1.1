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
        
        public OrderRequest() {}

      

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

