package com.shopzone.app.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "merchant_branches")
public class MerchantBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // MERCHANT
    // =========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    // =========================
    // BRANCH DETAILS
    // =========================

    @Column(nullable = false)
    private String branchName;

    @Column(unique = true)
    private String branchCode;

    // OWNED = Merchant's own branch
    // FRANCHISE = Franchise branch
    @Enumerated(EnumType.STRING)
    private BranchType branchType;

    private String franchiseOwnerName;

    private String franchiseOwnerEmail;

    private String franchiseOwnerMobile;

    // =========================
    // CONTACT DETAILS
    // =========================

    private String contactPersonName;

    private String contactNumber;

    private String alternateContactNumber;

    private String email;

    // =========================
    // ADDRESS
    // =========================

    private String addressLine1;

    private String addressLine2;

    private String landmark;

    private String city;

    private String district;

    private String state;

    private String country;

    private String pincode;

    // =========================
    // LOCATION
    // =========================

    private Double latitude;

    private Double longitude;

    // In KM
    private Double deliveryRadiusKm;

    // =========================
    // BUSINESS DETAILS
    // =========================

    private String gstNumber;

    private String fssaiNumber;

    private String licenseNumber;

    // =========================
    // DELIVERY SETTINGS
    // =========================

    private Boolean deliveryAvailable = true;

    private Boolean pickupAvailable = true;

    private Boolean dineInAvailable = false;

    // =========================
    // WORKING HOURS
    // =========================

    private String openingTime;

    private String closingTime;

    // Example: MON,TUE,WED,THU,FRI,SAT
    private String workingDays;

    // =========================
    // STATUS
    // =========================

    @Column(length = 1)
    private String isActive = "Y";

    @Column(length = 1)
    private String isDeleted = "N";

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
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

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
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

	public MerchantBranch(Long id, Merchant merchant, String branchName, String branchCode, BranchType branchType,
			String franchiseOwnerName, String franchiseOwnerEmail, String franchiseOwnerMobile,
			String contactPersonName, String contactNumber, String alternateContactNumber, String email,
			String addressLine1, String addressLine2, String landmark, String city, String district, String state,
			String country, String pincode, Double latitude, Double longitude, Double deliveryRadiusKm,
			String gstNumber, String fssaiNumber, String licenseNumber, Boolean deliveryAvailable,
			Boolean pickupAvailable, Boolean dineInAvailable, String openingTime, String closingTime,
			String workingDays, String isActive, String isDeleted, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.merchant = merchant;
		this.branchName = branchName;
		this.branchCode = branchCode;
		this.branchType = branchType;
		this.franchiseOwnerName = franchiseOwnerName;
		this.franchiseOwnerEmail = franchiseOwnerEmail;
		this.franchiseOwnerMobile = franchiseOwnerMobile;
		this.contactPersonName = contactPersonName;
		this.contactNumber = contactNumber;
		this.alternateContactNumber = alternateContactNumber;
		this.email = email;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.landmark = landmark;
		this.city = city;
		this.district = district;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.deliveryRadiusKm = deliveryRadiusKm;
		this.gstNumber = gstNumber;
		this.fssaiNumber = fssaiNumber;
		this.licenseNumber = licenseNumber;
		this.deliveryAvailable = deliveryAvailable;
		this.pickupAvailable = pickupAvailable;
		this.dineInAvailable = dineInAvailable;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
		this.workingDays = workingDays;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public MerchantBranch() {
		super();
	}

	@Override
	public String toString() {
		return "MerchantBranch [id=" + id + ", merchant=" + merchant + ", branchName=" + branchName + ", branchCode="
				+ branchCode + ", branchType=" + branchType + ", franchiseOwnerName=" + franchiseOwnerName
				+ ", franchiseOwnerEmail=" + franchiseOwnerEmail + ", franchiseOwnerMobile=" + franchiseOwnerMobile
				+ ", contactPersonName=" + contactPersonName + ", contactNumber=" + contactNumber
				+ ", alternateContactNumber=" + alternateContactNumber + ", email=" + email + ", addressLine1="
				+ addressLine1 + ", addressLine2=" + addressLine2 + ", landmark=" + landmark + ", city=" + city
				+ ", district=" + district + ", state=" + state + ", country=" + country + ", pincode=" + pincode
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", deliveryRadiusKm=" + deliveryRadiusKm
				+ ", gstNumber=" + gstNumber + ", fssaiNumber=" + fssaiNumber + ", licenseNumber=" + licenseNumber
				+ ", deliveryAvailable=" + deliveryAvailable + ", pickupAvailable=" + pickupAvailable
				+ ", dineInAvailable=" + dineInAvailable + ", openingTime=" + openingTime + ", closingTime="
				+ closingTime + ", workingDays=" + workingDays + ", isActive=" + isActive + ", isDeleted=" + isDeleted
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
    
    
    
    
}