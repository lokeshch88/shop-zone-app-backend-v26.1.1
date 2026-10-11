package com.shopzone.app.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressLine;
    private String addressLine2;
    private String city;
    private String district;
    private String state;
    private String country;
    
    private String pincode;
    private String type;
    private String landmark;
    	
    
    @Column(name = "is_primary_address")
    private String isPrimaryAddress;

    private Double latitude;
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "created_at", updatable = false)
   	private LocalDateTime createdAt;
   	 
   	 @Column(name = "updated_at")
   	private LocalDateTime updatedAt;
   	 
   	 private String name;
   	 private String phone;

    public Address() {}


    public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getLandmark() {
		return landmark;
	}


	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}


	public String getIsPrimaryAddress() {
		return isPrimaryAddress;
	}


	public void setIsPrimaryAddress(String isPrimaryAddress) {
		this.isPrimaryAddress = isPrimaryAddress;
	}


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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getAddressLine() { return addressLine; }

    public void setAddressLine(String addressLine) { this.addressLine = addressLine; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getPincode() { return pincode; }

    public void setPincode(String pincode) { this.pincode = pincode; }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}



	public String getAddressLine2() {
		return addressLine2;
	}


	@Override
	public String toString() {
		return "Address [id=" + id + ", addressLine=" + addressLine + ", addressLine2=" + addressLine2 + ", city="
				+ city + ", district=" + district + ", state=" + state + ", country=" + country + ", pincode=" + pincode
				+ ", type=" + type + ", isPrimaryAddress=" + isPrimaryAddress + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", user=" + user + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}


	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	
	
}

