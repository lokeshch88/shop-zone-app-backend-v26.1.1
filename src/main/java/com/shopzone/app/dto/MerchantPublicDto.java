package com.shopzone.app.dto;

public class MerchantPublicDto {
	//DTO to exposed for public url
	 private Long id;


	    private String businessName;
	    private String merchantCode;
	    
// flags
	
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
		return "MerchantPublicDto [id=" + id + ", businessName=" + businessName + ", merchantCode=" + merchantCode
				+ ", currency=" + currency + ", timezone=" + timezone + ", language=" + language + ", taxEnabled="
				+ taxEnabled + ", inventoryEnabled=" + inventoryEnabled + ", deliveryEnabled=" + deliveryEnabled
				+ ", onlinePaymentEnabled=" + onlinePaymentEnabled + ", cashOnDeliveryEnabled=" + cashOnDeliveryEnabled
				+ ", couponEnabled=" + couponEnabled + ", reviewEnabled=" + reviewEnabled + "]";
	}
    
    
    

}
