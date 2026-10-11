package com.shopzone.app.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MerchantDto {

    private Long id;

    // =========================
    // BASIC BUSINESS DETAILS
    // =========================

    private String businessName;
    private String merchantCode;

    private String businessType;
    private String description;

    // =========================
    // OWNER DETAILS
    // =========================

    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;

    // =========================
    // CONTACT DETAILS
    // =========================

    private String email;
    private String mobileNumber;
    private String alternateMobileNumber;

    // =========================
    // BUSINESS ADDRESS
    // =========================

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
    
    private String storeSlug;
    private String logoUrl;

    // =========================
    // BUSINESS REGISTRATION
    // =========================

    private String gstNumber;
    private String panNumber;
    private String fssaiNumber;
    private String licenseNumber;

    // =========================
    // BANK DETAILS
    // =========================

    private String bankAccountHolderName;
    private String bankAccountNumber;
    private String ifscCode;
    private String bankName;

    // =========================
    // SUBSCRIPTION / SAAS
    // =========================

    private String subscriptionPlan;

    private LocalDateTime subscriptionStartDate;
    private LocalDateTime subscriptionEndDate;

    private Integer maxBranches;
    private Integer maxProducts;
    private Integer maxUsers;

    // =========================
    // CUSTOM DOMAIN
    // =========================

    private String subdomain;

    private String customDomain;

    private Boolean customDomainEnabled;

    // =========================
    // STATUS
    // =========================

    private String active;

    private String isVerified;
    
    // Store Configuration
    // =========================

    private String currency;

    private String timezone;

    private String language;

    private Boolean taxEnabled;

    private Boolean inventoryEnabled;

    private Boolean deliveryEnabled;

    private Boolean onlinePaymentEnabled;

    private Boolean cashOnDeliveryEnabled;

    private Boolean couponEnabled;

    private Boolean reviewEnabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // =========================
    // BRANCHES
    // =========================

    private List<MerchantBranchDto> branches;

    
    //user details-Admin
    
    private String username;
    private String password;
    
    private String msg;
    
    
    
    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAlternateMobileNumber() {
		return alternateMobileNumber;
	}

	public void setAlternateMobileNumber(String alternateMobileNumber) {
		this.alternateMobileNumber = alternateMobileNumber;
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

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
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

	public String getBankAccountHolderName() {
		return bankAccountHolderName;
	}

	public void setBankAccountHolderName(String bankAccountHolderName) {
		this.bankAccountHolderName = bankAccountHolderName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(String subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}

	public LocalDateTime getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	public void setSubscriptionStartDate(LocalDateTime subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public LocalDateTime getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public void setSubscriptionEndDate(LocalDateTime subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public Integer getMaxBranches() {
		return maxBranches;
	}

	public void setMaxBranches(Integer maxBranches) {
		this.maxBranches = maxBranches;
	}

	public Integer getMaxProducts() {
		return maxProducts;
	}

	public void setMaxProducts(Integer maxProducts) {
		this.maxProducts = maxProducts;
	}

	public Integer getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(Integer maxUsers) {
		this.maxUsers = maxUsers;
	}

	public String getSubdomain() {
		return subdomain;
	}

	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	public String getCustomDomain() {
		return customDomain;
	}

	public void setCustomDomain(String customDomain) {
		this.customDomain = customDomain;
	}

	public Boolean getCustomDomainEnabled() {
		return customDomainEnabled;
	}

	public void setCustomDomainEnabled(Boolean customDomainEnabled) {
		this.customDomainEnabled = customDomainEnabled;
	}



	public String getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
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

	public List<MerchantBranchDto> getBranches() {
		return branches;
	}

	public void setBranches(List<MerchantBranchDto> branches) {
		this.branches = branches;
	}

	public String getStoreSlug() {
		return storeSlug;
	}

	public void setStoreSlug(String storeSlug) {
		this.storeSlug = storeSlug;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Boolean getTaxEnabled() {
		return taxEnabled;
	}

	public void setTaxEnabled(Boolean taxEnabled) {
		this.taxEnabled = taxEnabled;
	}

	public Boolean getInventoryEnabled() {
		return inventoryEnabled;
	}

	public void setInventoryEnabled(Boolean inventoryEnabled) {
		this.inventoryEnabled = inventoryEnabled;
	}

	public Boolean getDeliveryEnabled() {
		return deliveryEnabled;
	}

	public void setDeliveryEnabled(Boolean deliveryEnabled) {
		this.deliveryEnabled = deliveryEnabled;
	}

	public Boolean getOnlinePaymentEnabled() {
		return onlinePaymentEnabled;
	}

	public void setOnlinePaymentEnabled(Boolean onlinePaymentEnabled) {
		this.onlinePaymentEnabled = onlinePaymentEnabled;
	}

	public Boolean getCashOnDeliveryEnabled() {
		return cashOnDeliveryEnabled;
	}

	public void setCashOnDeliveryEnabled(Boolean cashOnDeliveryEnabled) {
		this.cashOnDeliveryEnabled = cashOnDeliveryEnabled;
	}

	public Boolean getCouponEnabled() {
		return couponEnabled;
	}

	public void setCouponEnabled(Boolean couponEnabled) {
		this.couponEnabled = couponEnabled;
	}

	public Boolean getReviewEnabled() {
		return reviewEnabled;
	}

	public void setReviewEnabled(Boolean reviewEnabled) {
		this.reviewEnabled = reviewEnabled;
	}

	@Override
	public String toString() {
		return "MerchantDto [id=" + id + ", businessName=" + businessName + ", merchantCode=" + merchantCode
				+ ", businessType=" + businessType + ", description=" + description + ", ownerName=" + ownerName
				+ ", ownerEmail=" + ownerEmail + ", ownerPhone=" + ownerPhone + ", email=" + email + ", mobileNumber="
				+ mobileNumber + ", alternateMobileNumber=" + alternateMobileNumber + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", landmark=" + landmark + ", city=" + city + ", district="
				+ district + ", state=" + state + ", country=" + country + ", pincode=" + pincode + ", latitude="
				+ latitude + ", longitude=" + longitude + ", storeSlug=" + storeSlug + ", logoUrl=" + logoUrl
				+ ", gstNumber=" + gstNumber + ", panNumber=" + panNumber + ", fssaiNumber=" + fssaiNumber
				+ ", licenseNumber=" + licenseNumber + ", bankAccountHolderName=" + bankAccountHolderName
				+ ", bankAccountNumber=" + bankAccountNumber + ", ifscCode=" + ifscCode + ", bankName=" + bankName
				+ ", subscriptionPlan=" + subscriptionPlan + ", subscriptionStartDate=" + subscriptionStartDate
				+ ", subscriptionEndDate=" + subscriptionEndDate + ", maxBranches=" + maxBranches + ", maxProducts="
				+ maxProducts + ", maxUsers=" + maxUsers + ", subdomain=" + subdomain + ", customDomain=" + customDomain
				+ ", customDomainEnabled=" + customDomainEnabled + ", isActive=" + active + ", isVerified="
				+ isVerified + ", currency=" + currency + ", timezone=" + timezone + ", language=" + language
				+ ", taxEnabled=" + taxEnabled + ", inventoryEnabled=" + inventoryEnabled + ", deliveryEnabled="
				+ deliveryEnabled + ", onlinePaymentEnabled=" + onlinePaymentEnabled + ", cashOnDeliveryEnabled="
				+ cashOnDeliveryEnabled + ", couponEnabled=" + couponEnabled + ", reviewEnabled=" + reviewEnabled
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", branches=" + branches + ", username="
				+ username + ", password=" + password + "]";
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}


    
    
}