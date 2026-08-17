package com.shopzone.app.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Store list of product IDs (simple numbers)
//    @ElementCollection
//    @CollectionTable(name = "order_product_ids", joinColumns = @JoinColumn(name = "order_id"))
//    @Column(name = "product_id")
//    private List<Long> productIds;
                                                                                                                                                                                                                                  
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private Double totalAmount;
    
    @Column(name="order_id", nullable = false, unique = true)
    private String orderId;
    
    @Column(name = "created_at", updatable = false)
   	private LocalDateTime createdAt;
   	 
   	 @Column(name = "updated_at")
   	private LocalDateTime updatedAt;

   	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
   	private List<OrderItem> items;

    @Column(name = "address_id")
    private Long addressId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="delivery_id" )
    @JsonIgnore
    private Delivery deliveryId;
    
    
   	public Delivery getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Delivery deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public List<OrderItem> getItems() {
   	    return items;
   	}

   	public void setItems(List<OrderItem> items) {
   	    this.items = items;
   	}

    public Order() {}

   

    public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}



	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

    public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

	@Override
	public String toString() {
		return " [id=" + id + ", user=" + user + ", status=" + status + ", totalAmount=" + totalAmount
				+ ", orderId=" + orderId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", items=" + items
				+ ", addressId=" + addressId + "]";
	}
    
    
}
