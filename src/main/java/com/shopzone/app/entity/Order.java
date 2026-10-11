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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_id" )
//    @JsonIgnore
    private Merchant merchant;
    
    //payment details
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
    
    
    
   	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

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
		return "Order [id=" + id + ", user=" + user + ", status=" + status + ", totalAmount=" + totalAmount
				+ ", orderId=" + orderId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", items=" + items
				+ ", addressId=" + addressId + ", deliveryId=" + deliveryId + ", merchant=" + merchant
				+ ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus + ", paymentId=" + paymentId
				+ ", paymentSignature=" + paymentSignature + ", currency=" + currency + ", transactionId="
				+ transactionId + ", paymentGateway=" + paymentGateway + ", paymentError=" + paymentError
				+ ", paymentDateTime=" + paymentDateTime + "]";
	}

	
    
    
}
