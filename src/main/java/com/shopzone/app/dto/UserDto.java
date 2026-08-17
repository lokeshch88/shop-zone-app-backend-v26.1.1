package com.shopzone.app.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.*;



public class UserDto {
	 	//@NotBlank(message = "Username is required")
	 	private String username;
	   
	   // @NotBlank(message = "Password is required")
	    private String password;

	    @Email(message = "Email should be valid")
	    private String email;
	    
//	    @NotBlank(message = "Mobile No. is required")
	    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
	    private String mobile;
	    
	    private String firstName;
	    private String lastName;
	    private int age;

		private String gender;

		private String street;

		private String city;

		private String pincode;
		
		private LocalDateTime updatedAt;
		
		private String token;
		
		private String msg;
		private String error;

		private String otp;
		
		private Long userId;

		private String userRole;
		private List<AddressDto> addresses;
		
		public UserDto(String string) {
			
		}

		public UserDto() {
			// TODO Auto-generated constructor stub
		}

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

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPincode() {
			return pincode;
		}

		public void setPincode(String pincode) {
			this.pincode = pincode;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getOtp() {
			return otp;
		}

		public void setOtp(String otp) {
			this.otp = otp;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getUserRole() {
			return userRole;
		}

		public void setUserRole(String userRole) {
			this.userRole = userRole;
		}

		public List<AddressDto> getAddresses() {
			return addresses;
		}

		public void setAddresses(List<AddressDto> addresses) {
			this.addresses = addresses;
		}
		
		
}
