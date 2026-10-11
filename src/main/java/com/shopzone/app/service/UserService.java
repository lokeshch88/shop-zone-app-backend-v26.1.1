package com.shopzone.app.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopzone.app.controller.UserController;
import com.shopzone.app.dto.UserDto;
import com.shopzone.app.entity.Merchant;
import com.shopzone.app.entity.User;
import com.shopzone.app.exceptions.UserNotFoundException;
import com.shopzone.app.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	public String registerUser(UserDto userDto, Merchant merchant) {
		try {
//			 check if email already exists
			if (userRepo.existsByEmailAndMerchant(userDto.getEmail(), merchant)) {
				return "Email already in use: " + userDto.getEmail();
			}
//			 check if username already exists
			if (userRepo.existsByUsernameAndMerchant(userDto.getUsername(), merchant)) {
				return "Username is already taken: " + userDto.getUsername();
			}

			User user = new User();
			String encodedPassword = passwordEncoder.encode(userDto.getPassword());

			user.setEmail(userDto.getEmail());
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setCreatedAt(LocalDateTime.now()); // set entry date time as created

			user.setPassword(encodedPassword);
			user.setAge(0);
			user.setUsername(userDto.getUsername());
			user.setRole(userDto.getUserRole());
			user.setMerchant(merchant);

			userRepo.save(user); // save
			return "User saved successfully";
		} catch (DataIntegrityViolationException ex) {
			System.out.printf("Unique constraint violation for user registration: {}", ex.getMessage());
			return "Registration failed due to duplicate data.";
		} catch (Exception ex) {
			return ("Error in user service" + ex);
		}

	}

	public UserDetails loadUserByUsername(String username) {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Arrays.stream(user.getRole().split(",")).map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
						.collect(Collectors.toList()));
	}

	public String updateUser(Long userId, UserDto userDto) {
		// Fetch the user from the database
		Optional<User> userOptional = userRepo.findById(userId);
		if (!userOptional.isPresent()) {
			return ("User not found with id: " + userId);
		}

		User user = userOptional.get();

		// Update the user details
		user.setUsername(userDto.getUsername());
		// user.setPassword(passwordEncoder.encode(userDto.getPassword())); //
		// Re-encrypt the password
		user.setEmail(userDto.getEmail());
		user.setMobile(userDto.getMobile());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		// user.setAge(userDto.getAge());
		// user.setGender(userDto.getGender());
		user.setStreet(userDto.getStreet());
		user.setCity(userDto.getCity());
		user.setPincode(userDto.getPincode());
		user.setUpdatedAt(LocalDateTime.now());

		// Save the updated user back to the database
		userRepo.save(user);

		return "User updated successfully.";
	}

	public String deleteUser(Long userId) {
		try {
			userRepo.deleteById(userId);
			return "User deleted successfully";
		} catch (Exception e) {
			return "Error while deleting user";
		}

	}

	public String resetPassword(String usernameOrEmail, String newPassword) {
		Optional<User> userOptional = userRepo.findByUsername(usernameOrEmail);//check with username

		if (!userOptional.isPresent()) {
			userOptional = userRepo.findByEmail(usernameOrEmail); //check with email
		}

		if (!userOptional.isPresent()) {
			return ("No user found with the given username/email.");
		}

		User user = userOptional.get();

		// Encrypt and update password
		user.setPassword(passwordEncoder.encode(newPassword));

		// Save updated user
		userRepo.save(user);

		return "Password reset successfully.";
	}

	public List<UserDto> getAllUsers(String merchantSlug) {
		
		log.info("Fetching all users for merchant: {}", merchantSlug);
		try {
		Merchant merchant = authenticationService.getCurrentMerchant();
		
		List<User> users = userRepo.findAllByMerchant(merchant);

		 return users.stream()
	                .map(user -> modelMapper.map(user, UserDto.class))
	                .collect(Collectors.toList());
//		return users;
		}
		catch (Exception e) {
			log.error("Error fetching users for merchant {}: {}", merchantSlug, e.getMessage());
			throw new UserNotFoundException("Error fetching users for merchant: " + merchantSlug);
		}
	}

	public UserDto getUserById(Long userId) {
		Optional<User> userOptional = userRepo.findById(userId);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found with id: " + userId);
		}

		User user = userOptional.get();

		// Convert User entity to UserDto
		UserDto userDto = new UserDto();
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setMobile(user.getMobile());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setAge(user.getAge());
		userDto.setGender(user.getGender());
		userDto.setStreet(user.getStreet());
		userDto.setCity(user.getCity());
		userDto.setPincode(user.getPincode());
		userDto.setUpdatedAt(user.getUpdatedAt());

		return userDto;
	}

	public List<UserDto> getAllDeliveryPartners() {
		List<User> users = userRepo.findAll();

		 return users.stream()
				 .filter(user-> "Delivery User".equals(user.getRole()) )
	                .map(user -> modelMapper.map(user, UserDto.class))
	                .collect(Collectors.toList());
	}

	public boolean existsByUsername(String username) {
		
		return userRepo.existsByUsername(username);
	}

}
