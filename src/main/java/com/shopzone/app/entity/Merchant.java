package com.shopzone.app.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // Business Information
    // =========================

    @Column(nullable = false, unique = true, length = 100)
    private String merchantCode;

    @Column(nullable = false, length = 150)
    private String businessName;

    @Column(length = 150)
    private String legalBusinessName;

    @Column(length = 100)
    private String businessType;
    // FOOD, GROCERY, GARMENTS, ELECTRONICS, GENERAL, etc.

    @Column(length = 100)
    private String businessCategory;

    @Column(length = 500)
    private String description;

    private String logoUrl;

    private String faviconUrl;

    // =========================
    // Owner Information
    // =========================

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String ownerEmail;

    @Column(nullable = false)
    private String ownerPhone;

    // =========================
    // Address
    // =========================

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String pincode;

    private Double latitude;

    private Double longitude;

    // =========================
    // Indian Business Details
    // =========================

    private String gstNumber;

    private String panNumber;

    private String businessRegistrationNumber;

    // =========================
    // Website / Store
    // =========================

    @Column(unique = true)
    private String storeSlug;

    private String storeUrl;

    private String customDomain;

    private Boolean customDomainEnabled;

    private Boolean sslEnabled;

    // =========================
    // Subscription
    // =========================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionPlan subscriptionPlan;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    private LocalDate subscriptionStartDate;

    private LocalDate subscriptionEndDate;

    // =========================
    // Usage Limits
    // =========================

    private Long maxProducts;

    private Long maxUsers;

    private Long maxOrdersPerMonth;

    private Long maxStorageMb;

    private Long maxApiRequestsPerMonth;

    // =========================
    // Current Usage
    // =========================

    private Long productCount;

    private Long userCount;

    private Long orderCount;

    private Long storageUsedMb;

    private Long apiRequestsUsed;

    // =========================
    // Billing
    // =========================

    private BigDecimal monthlySubscription;

    private BigDecimal additionalStorageCharges;

    private BigDecimal additionalUserCharges;

    private BigDecimal additionalOrderCharges;

    private BigDecimal totalMonthlyBill;

    // =========================
    // Payment
    // =========================

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDate lastPaymentDate;

    private LocalDate nextBillingDate;

    private String paymentCustomerId;

    private String paymentSubscriptionId;

    // =========================
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
    
    private int maxImagesPerProduct;

    // =========================
    // Branding
    // =========================

    private String primaryColor;

    private String secondaryColor;

    private String fontFamily;

    // =========================
    // Status
    // =========================

    @Enumerated(EnumType.STRING)
    private MerchantStatus status;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (active == null) {
            active = true;
        }

        if (currency == null) {
            currency = "INR";
        }

        if (country == null) {
            country = "India";
        }

        if (timezone == null) {
            timezone = "Asia/Kolkata";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    @OneToMany(
            mappedBy = "merchant",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MerchantBranch> branches = new ArrayList<>();
    
    @OneToMany(
            mappedBy = "merchant",
            fetch = FetchType.LAZY
    )
    private List<User> users = new ArrayList<>();

    
    @JsonIgnore
    @OneToMany(
            mappedBy = "merchant",
            fetch = FetchType.LAZY
    )
    private List<Category> categories = new ArrayList<>();

 
    
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getLegalBusinessName() {
		return legalBusinessName;
	}

	public void setLegalBusinessName(String legalBusinessName) {
		this.legalBusinessName = legalBusinessName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getFaviconUrl() {
		return faviconUrl;
	}

	public void setFaviconUrl(String faviconUrl) {
		this.faviconUrl = faviconUrl;
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

	public String getBusinessRegistrationNumber() {
		return businessRegistrationNumber;
	}

	public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
		this.businessRegistrationNumber = businessRegistrationNumber;
	}

	public String getStoreSlug() {
		return storeSlug;
	}

	public void setStoreSlug(String storeSlug) {
		this.storeSlug = storeSlug;
	}

	public String getStoreUrl() {
		return storeUrl;
	}

	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
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

	public Boolean getSslEnabled() {
		return sslEnabled;
	}

	public void setSslEnabled(Boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}

	public SubscriptionPlan getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}

	public SubscriptionStatus getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public LocalDate getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	public void setSubscriptionStartDate(LocalDate subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public LocalDate getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public void setSubscriptionEndDate(LocalDate subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public Long getMaxProducts() {
		return maxProducts;
	}

	public void setMaxProducts(Long maxProducts) {
		this.maxProducts = maxProducts;
	}

	public Long getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(Long maxUsers) {
		this.maxUsers = maxUsers;
	}

	public Long getMaxOrdersPerMonth() {
		return maxOrdersPerMonth;
	}

	public void setMaxOrdersPerMonth(Long maxOrdersPerMonth) {
		this.maxOrdersPerMonth = maxOrdersPerMonth;
	}

	public Long getMaxStorageMb() {
		return maxStorageMb;
	}

	public void setMaxStorageMb(Long maxStorageMb) {
		this.maxStorageMb = maxStorageMb;
	}

	public Long getMaxApiRequestsPerMonth() {
		return maxApiRequestsPerMonth;
	}

	public void setMaxApiRequestsPerMonth(Long maxApiRequestsPerMonth) {
		this.maxApiRequestsPerMonth = maxApiRequestsPerMonth;
	}

	public Long getProductCount() {
		return productCount;
	}

	public void setProductCount(Long productCount) {
		this.productCount = productCount;
	}

	public Long getUserCount() {
		return userCount;
	}

	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}

	public Long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}

	public Long getStorageUsedMb() {
		return storageUsedMb;
	}

	public void setStorageUsedMb(Long storageUsedMb) {
		this.storageUsedMb = storageUsedMb;
	}

	public Long getApiRequestsUsed() {
		return apiRequestsUsed;
	}

	public void setApiRequestsUsed(Long apiRequestsUsed) {
		this.apiRequestsUsed = apiRequestsUsed;
	}

	public BigDecimal getMonthlySubscription() {
		return monthlySubscription;
	}

	public void setMonthlySubscription(BigDecimal monthlySubscription) {
		this.monthlySubscription = monthlySubscription;
	}

	public BigDecimal getAdditionalStorageCharges() {
		return additionalStorageCharges;
	}

	public void setAdditionalStorageCharges(BigDecimal additionalStorageCharges) {
		this.additionalStorageCharges = additionalStorageCharges;
	}

	public BigDecimal getAdditionalUserCharges() {
		return additionalUserCharges;
	}

	public void setAdditionalUserCharges(BigDecimal additionalUserCharges) {
		this.additionalUserCharges = additionalUserCharges;
	}

	public BigDecimal getAdditionalOrderCharges() {
		return additionalOrderCharges;
	}

	public void setAdditionalOrderCharges(BigDecimal additionalOrderCharges) {
		this.additionalOrderCharges = additionalOrderCharges;
	}

	public BigDecimal getTotalMonthlyBill() {
		return totalMonthlyBill;
	}

	public void setTotalMonthlyBill(BigDecimal totalMonthlyBill) {
		this.totalMonthlyBill = totalMonthlyBill;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDate getLastPaymentDate() {
		return lastPaymentDate;
	}

	public void setLastPaymentDate(LocalDate lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	public LocalDate getNextBillingDate() {
		return nextBillingDate;
	}

	public void setNextBillingDate(LocalDate nextBillingDate) {
		this.nextBillingDate = nextBillingDate;
	}

	public String getPaymentCustomerId() {
		return paymentCustomerId;
	}

	public void setPaymentCustomerId(String paymentCustomerId) {
		this.paymentCustomerId = paymentCustomerId;
	}

	public String getPaymentSubscriptionId() {
		return paymentSubscriptionId;
	}

	public void setPaymentSubscriptionId(String paymentSubscriptionId) {
		this.paymentSubscriptionId = paymentSubscriptionId;
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

	public String getPrimaryColor() {
		return primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	public String getSecondaryColor() {
		return secondaryColor;
	}

	public void setSecondaryColor(String secondaryColor) {
		this.secondaryColor = secondaryColor;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public MerchantStatus getStatus() {
		return status;
	}

	public void setStatus(MerchantStatus status) {
		this.status = status;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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

	public List<MerchantBranch> getBranches() {
		return branches;
	}

	public void setBranches(List<MerchantBranch> branches) {
		this.branches = branches;
	}

	public Merchant(Long id, String merchantCode, String businessName, String legalBusinessName, String businessType,
			String businessCategory, String description, String logoUrl, String faviconUrl, String ownerName,
			String ownerEmail, String ownerPhone, String addressLine1, String addressLine2, String city, String state,
			String country, String pincode, Double latitude, Double longitude, String gstNumber, String panNumber,
			String businessRegistrationNumber, String storeSlug, String storeUrl, String customDomain,
			Boolean customDomainEnabled, Boolean sslEnabled, SubscriptionPlan subscriptionPlan,
			SubscriptionStatus subscriptionStatus, LocalDate subscriptionStartDate, LocalDate subscriptionEndDate,
			Long maxProducts, Long maxUsers, Long maxOrdersPerMonth, Long maxStorageMb, Long maxApiRequestsPerMonth,
			Long productCount, Long userCount, Long orderCount, Long storageUsedMb, Long apiRequestsUsed,
			BigDecimal monthlySubscription, BigDecimal additionalStorageCharges, BigDecimal additionalUserCharges,
			BigDecimal additionalOrderCharges, BigDecimal totalMonthlyBill, PaymentStatus paymentStatus,
			LocalDate lastPaymentDate, LocalDate nextBillingDate, String paymentCustomerId,
			String paymentSubscriptionId, String currency, String timezone, String language, Boolean taxEnabled,
			Boolean inventoryEnabled, Boolean deliveryEnabled, Boolean onlinePaymentEnabled,
			Boolean cashOnDeliveryEnabled, Boolean couponEnabled, Boolean reviewEnabled, String primaryColor,
			String secondaryColor, String fontFamily, MerchantStatus status, Boolean active, LocalDateTime createdAt,
			LocalDateTime updatedAt, List<MerchantBranch> branches) {
		super();
		this.id = id;
		this.merchantCode = merchantCode;
		this.businessName = businessName;
		this.legalBusinessName = legalBusinessName;
		this.businessType = businessType;
		this.businessCategory = businessCategory;
		this.description = description;
		this.logoUrl = logoUrl;
		this.faviconUrl = faviconUrl;
		this.ownerName = ownerName;
		this.ownerEmail = ownerEmail;
		this.ownerPhone = ownerPhone;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.gstNumber = gstNumber;
		this.panNumber = panNumber;
		this.businessRegistrationNumber = businessRegistrationNumber;
		this.storeSlug = storeSlug;
		this.storeUrl = storeUrl;
		this.customDomain = customDomain;
		this.customDomainEnabled = customDomainEnabled;
		this.sslEnabled = sslEnabled;
		this.subscriptionPlan = subscriptionPlan;
		this.subscriptionStatus = subscriptionStatus;
		this.subscriptionStartDate = subscriptionStartDate;
		this.subscriptionEndDate = subscriptionEndDate;
		this.maxProducts = maxProducts;
		this.maxUsers = maxUsers;
		this.maxOrdersPerMonth = maxOrdersPerMonth;
		this.maxStorageMb = maxStorageMb;
		this.maxApiRequestsPerMonth = maxApiRequestsPerMonth;
		this.productCount = productCount;
		this.userCount = userCount;
		this.orderCount = orderCount;
		this.storageUsedMb = storageUsedMb;
		this.apiRequestsUsed = apiRequestsUsed;
		this.monthlySubscription = monthlySubscription;
		this.additionalStorageCharges = additionalStorageCharges;
		this.additionalUserCharges = additionalUserCharges;
		this.additionalOrderCharges = additionalOrderCharges;
		this.totalMonthlyBill = totalMonthlyBill;
		this.paymentStatus = paymentStatus;
		this.lastPaymentDate = lastPaymentDate;
		this.nextBillingDate = nextBillingDate;
		this.paymentCustomerId = paymentCustomerId;
		this.paymentSubscriptionId = paymentSubscriptionId;
		this.currency = currency;
		this.timezone = timezone;
		this.language = language;
		this.taxEnabled = taxEnabled;
		this.inventoryEnabled = inventoryEnabled;
		this.deliveryEnabled = deliveryEnabled;
		this.onlinePaymentEnabled = onlinePaymentEnabled;
		this.cashOnDeliveryEnabled = cashOnDeliveryEnabled;
		this.couponEnabled = couponEnabled;
		this.reviewEnabled = reviewEnabled;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.fontFamily = fontFamily;
		this.status = status;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.branches = branches;
	}

	public Merchant() {
		super();
	}

	@Override
	public String toString() {
		return "Merchant [id=" + id + ", merchantCode=" + merchantCode + ", businessName=" + businessName
				+ ", legalBusinessName=" + legalBusinessName + ", businessType=" + businessType + ", businessCategory="
				+ businessCategory + ", description=" + description + ", logoUrl=" + logoUrl + ", faviconUrl="
				+ faviconUrl + ", ownerName=" + ownerName + ", ownerEmail=" + ownerEmail + ", ownerPhone=" + ownerPhone
				+ ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city=" + city + ", state="
				+ state + ", country=" + country + ", pincode=" + pincode + ", latitude=" + latitude + ", longitude="
				+ longitude + ", gstNumber=" + gstNumber + ", panNumber=" + panNumber + ", businessRegistrationNumber="
				+ businessRegistrationNumber + ", storeSlug=" + storeSlug + ", storeUrl=" + storeUrl + ", customDomain="
				+ customDomain + ", customDomainEnabled=" + customDomainEnabled + ", sslEnabled=" + sslEnabled
				+ ", subscriptionPlan=" + subscriptionPlan + ", subscriptionStatus=" + subscriptionStatus
				+ ", subscriptionStartDate=" + subscriptionStartDate + ", subscriptionEndDate=" + subscriptionEndDate
				+ ", maxProducts=" + maxProducts + ", maxUsers=" + maxUsers + ", maxOrdersPerMonth=" + maxOrdersPerMonth
				+ ", maxStorageMb=" + maxStorageMb + ", maxApiRequestsPerMonth=" + maxApiRequestsPerMonth
				+ ", productCount=" + productCount + ", userCount=" + userCount + ", orderCount=" + orderCount
				+ ", storageUsedMb=" + storageUsedMb + ", apiRequestsUsed=" + apiRequestsUsed + ", monthlySubscription="
				+ monthlySubscription + ", additionalStorageCharges=" + additionalStorageCharges
				+ ", additionalUserCharges=" + additionalUserCharges + ", additionalOrderCharges="
				+ additionalOrderCharges + ", totalMonthlyBill=" + totalMonthlyBill + ", paymentStatus=" + paymentStatus
				+ ", lastPaymentDate=" + lastPaymentDate + ", nextBillingDate=" + nextBillingDate
				+ ", paymentCustomerId=" + paymentCustomerId + ", paymentSubscriptionId=" + paymentSubscriptionId
				+ ", currency=" + currency + ", timezone=" + timezone + ", language=" + language + ", taxEnabled="
				+ taxEnabled + ", inventoryEnabled=" + inventoryEnabled + ", deliveryEnabled=" + deliveryEnabled
				+ ", onlinePaymentEnabled=" + onlinePaymentEnabled + ", cashOnDeliveryEnabled=" + cashOnDeliveryEnabled
				+ ", couponEnabled=" + couponEnabled + ", reviewEnabled=" + reviewEnabled + ",  primaryColor="
				+ primaryColor + ", secondaryColor=" + secondaryColor + ", fontFamily=" + fontFamily + ", status="
				+ status + ", active=" + active + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", branches=" + branches + "]";
	}

	public int getMaxImagesPerProduct() {
		return maxImagesPerProduct;
	}

	public void setMaxImagesPerProduct(int maxImagesPerProduct) {
		this.maxImagesPerProduct = maxImagesPerProduct;
	}
    
    
    
}