package com.shopzone.app.dto;

import com.shopzone.app.entity.BranchType;

public class MerchantBranchDto {

    private Long id;

    private Long merchantId;

    private String branchName;
    private String branchCode;

    private BranchType branchType;

    private String franchiseOwnerName;
    private String franchiseOwnerEmail;
    private String franchiseOwnerMobile;

    private String contactPersonName;
    private String contactNumber;
    private String alternateContactNumber;
    private String email;

    private String addressLine1;
    private String addressLine2;
    private String landmark;

    private String city;
    private String district;
    private String state;
    private String country;
    private String pincode;

    private Double latitude;
    private Double longitude;

    private Double deliveryRadiusKm;

    private String gstNumber;
    private String fssaiNumber;
    private String licenseNumber;

    private Boolean deliveryAvailable;
    private Boolean pickupAvailable;
    private Boolean dineInAvailable;

    private String openingTime;
    private String closingTime;
    private String workingDays;

    private String isActive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public BranchType getBranchType() {
		return branchType;
	}

	public void setBranchType(BranchType branchType) {
		this.branchType = branchType;
	}

	public String getFranchiseOwnerName() {
		return franchiseOwnerName;
	}

	public void setFranchiseOwnerName(String franchiseOwnerName) {
		this.franchiseOwnerName = franchiseOwnerName;
	}

	public String getFranchiseOwnerEmail() {
		return franchiseOwnerEmail;
	}

	public void setFranchiseOwnerEmail(String franchiseOwnerEmail) {
		this.franchiseOwnerEmail = franchiseOwnerEmail;
	}

	public String getFranchiseOwnerMobile() {
		return franchiseOwnerMobile;
	}

	public void setFranchiseOwnerMobile(String franchiseOwnerMobile) {
		this.franchiseOwnerMobile = franchiseOwnerMobile;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAlternateContactNumber() {
		return alternateContactNumber;
	}

	public void setAlternateContactNumber(String alternateContactNumber) {
		this.alternateContactNumber = alternateContactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getDeliveryRadiusKm() {
		return deliveryRadiusKm;
	}

	public void setDeliveryRadiusKm(Double deliveryRadiusKm) {
		this.deliveryRadiusKm = deliveryRadiusKm;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getFssaiNumber() {
		return fssaiNumber;
	}

	public void setFssaiNumber(String fssaiNumber) {
		this.fssaiNumber = fssaiNumber;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Boolean getDeliveryAvailable() {
		return deliveryAvailable;
	}

	public void setDeliveryAvailable(Boolean deliveryAvailable) {
		this.deliveryAvailable = deliveryAvailable;
	}

	public Boolean getPickupAvailable() {
		return pickupAvailable;
	}

	public void setPickupAvailable(Boolean pickupAvailable) {
		this.pickupAvailable = pickupAvailable;
	}

	public Boolean getDineInAvailable() {
		return dineInAvailable;
	}

	public void setDineInAvailable(Boolean dineInAvailable) {
		this.dineInAvailable = dineInAvailable;
	}

	public String getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}

	public String getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(String closingTime) {
		this.closingTime = closingTime;
	}

	public String getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(String workingDays) {
		this.workingDays = workingDays;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
    
    
    
}