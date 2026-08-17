package com.shopzone.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.shopzone.app.dto.AddressDto;
import com.shopzone.app.dto.EmailRequest;
import com.shopzone.app.dto.EmailResponse;
import com.shopzone.app.dto.ResponseDto;
import com.shopzone.app.dto.UserDto;
import com.shopzone.app.dto.UserResetPassword;
import com.shopzone.app.dto.VerifyEmailOtpRequest;
import com.shopzone.app.entity.User;
import com.shopzone.app.repo.UserRepo;
import com.shopzone.app.service.EmailService;
import com.shopzone.app.service.GeneralParameterService;
import com.shopzone.app.service.UserService;
import com.shopzone.app.utils.JwtUtil;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

//	@PostMapping(path = "/login")
//	public String login(@RequestBody UserDto user){
//		try {
//		  Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
//		    
//		    if (!foundUser.isPresent() || !passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword())) {
//		        throw new BadCredentialsException("Invalid username or password");
//		    }
//		String token=jwtUtil.generateToken(user);
//		return token;
//		}catch (Exception e) {
//			return e.getMessage();
//		}
//	}

	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@Valid @RequestBody UserDto userDto) {
		try {
			// Authenticate the user based on the username and password
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

			// If authentication is successful, generate JWT token
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtUtil.generateToken(userDto);

			// fetch user details to send resp
			Optional<User> userOpt = userRepo.findByUsername(userDto.getUsername());
			if (!userOpt.isPresent()) {
				log.warn("Authenticated but user not found in DB");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserDto("User not found"));
			}

			User user = userOpt.get();

			// UserDto userResponseDto = new UserDto();
			// userResponseDto.setUsername(userDto.getUsername());
			userDto.setToken(token);
			userDto.setPassword(null);
			userDto.setUsername(user.getUsername());
			userDto.setFirstName(user.getFirstName());
			userDto.setLastName(user.getLastName());
			userDto.setEmail(user.getEmail());
			userDto.setUserId(user.getId());
			userDto.setUserRole(user.getRole());

	        List<AddressDto> addressDTOs = user.getAddresses()
	            .stream()
	            .map(addr -> {
	            	AddressDto a = new AddressDto();
	                a.setId(addr.getId());
	                a.setAddressLine(addr.getAddressLine());
	                a.setCity(addr.getCity());
	                a.setDistrict(addr.getDistrict());
	                a.setState(addr.getState());
	                a.setCountry(addr.getCountry());
	                a.setPincode(addr.getPincode());
	                a.setLatitude(addr.getLatitude());
	                a.setLongitude(addr.getLongitude());
	                return a;
	            })
	            .collect(Collectors.toList());

	        userDto.setAddresses(addressDTOs);

			userDto.setMsg("Welcome " + userDto.getUsername());

			log.info("User logged in successfully with username: " + userDto.getUsername());

			return ResponseEntity.ok(userDto); // Return token in response

		} catch (BadCredentialsException e) {
			userDto.setMsg("Invalid username or password");
			userDto.setPassword(null);
			log.warn("Invalid username or password");

			return ResponseEntity.badRequest().body(userDto);
		} catch (Exception e) {
			log.error("Error while login user " + userDto.getUsername());
			userDto.setMsg("Server error while  login");
			userDto.setPassword(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userDto);
		}

	}

	@PostMapping("/check-availability")
	public ResponseEntity<UserDto> checkUsernameEmailAvailability(@RequestBody UserDto user) {
		try {
			if (userRepo.existsByUsername(user.getUsername())) {
				user.setMsg("Username is already taken: " + user.getUsername());

				log.info("Response msg: {}", user.getMsg());
				return ResponseEntity.ok(user);
			}
//			if (userRepo.existsByEmail(user.getEmail())) {
//				user.setMsg("Email already in use: " + user.getEmail());
//
//				log.info("Response msg: {}", user.getMsg());
//				return ResponseEntity.ok(user);
//			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		log.info("Username and email are available.");
		return ResponseEntity.ok().build();

	}

	@PostMapping("/register")
	// @PreAuthorize("hasRole('ADMIN')")
	// @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
		UserDto userResponseDto = new UserDto();
		try {
			log.info("In user Registration for username " + userDto.getUsername());

			// otp verification reqd before registartion
			// check otp from email service

			String msg = userService.registerUser(userDto);

			userResponseDto.setUsername(userDto.getUsername());
			userResponseDto.setMsg(msg);

			// if user reg. successful
			if ("User saved successfully".equals(msg)) {
				log.info("Registration successful with username " + userDto.getUsername());
				// send registration welcome email
				emailService.sendWelcomeEmail(userDto);
			}

			return ResponseEntity.ok(userResponseDto);

		} catch (AccessDeniedException e) {
			userResponseDto.setMsg("Your not an authorized person");
			log.warn("Your not an authorized person " + e);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userResponseDto);
		} catch (Exception ex) {
			userResponseDto.setMsg("error while register " + ex);
			log.error("error while register " + ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userResponseDto);
		}

	}

	// Update User Info
	@PutMapping("/{userId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
		try {
			String msg = userService.updateUser(userId, userDto);
			userDto.setMsg(msg);
			return ResponseEntity.ok(userDto);
		} catch (Exception e) {
			userDto.setMsg("Error while updating user: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userDto);
		}
	}

	// Delete User
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')") // Example: only ADMIN can delete a user
	public ResponseEntity<UserDto> deleteUser(@PathVariable Long userId) {
		UserDto userDto = new UserDto();
		try {
			String msg = userService.deleteUser(userId);

			userDto.setMsg(msg);
			return ResponseEntity.ok(userDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userDto);
		}
	}

	// Get User by ID
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
		try {
			UserDto userDto = userService.getUserById(userId);
			return ResponseEntity.ok(userDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// Get User by Username (For login or display)
//    @GetMapping("/username/{username}")
//    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
//        try {
//            UserDto userDto = userService.getUserByUsername(username);
//            return ResponseEntity.ok(userDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

	// Reset Password (Forgot Password)
	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody UserResetPassword userDto) {
		try {
			// verify otp from email sevice
//        	ResponseEntity<EmailResponse> response = restTemplate.postForEntity(
//        		    "http://localhost:8081/email/send",
//        		    emailRequest,
//        		    EmailResponse.class
//        		);
//l
//        		if (response.getStatusCode().is2xxSuccessful()) {
//        		    EmailResponse body = response.getBody();
//        		    System.out.println("Email status: " + body.getStatus());
//        		    System.out.println("Message: " + body.getMessage());
//        		}
			// if otp verified proceed

			String msg = userService.resetPassword(userDto.getUsernameOrEmail(), userDto.getNewPassword());
			return ResponseEntity.ok(msg);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while resetting password: " + e.getMessage());
		}
	}

	// Get All Users (Admin-only)
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')") // Only admins can get all users
	public ResponseEntity<List<UserDto>> getAllUsers() {
		try {
			List<UserDto> users = userService.getAllUsers();
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping("/delivery-partners")
	//@PreAuthorize("hasRole('ADMIN')") // Only admins can get all users
	public ResponseEntity<List<UserDto>> getAllDeliveryPartners() {
		try {
			List<UserDto> users = userService.getAllDeliveryPartners();
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// Change User Role (Admin only)
//    @PutMapping("/change-role/{userId}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<String> changeUserRole(@PathVariable Long userId, @RequestParam String role) {
//        try {
//            String msg = userService.changeUserRole(userId, role);
//          return ResponseEntity.ok(msg);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while changing user role: " + e.getMessage());
//        }
//    }

	@PostMapping("/send-otp")
	public ResponseEntity<?> sendOtp(@RequestBody UserDto user) {
		log.info("Otp request for email " + user.getEmail());
		EmailRequest req = new EmailRequest();
		req.setEmail(user.getEmail());
		String resp = emailService.sendOtp(req);
		user.setMsg(resp);
		return ResponseEntity.ok(user);

	}

	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody UserDto user) {
		try {
			log.info("Otp verify method for email " + user.getEmail());
			VerifyEmailOtpRequest req = new VerifyEmailOtpRequest();
			req.setEmail(user.getEmail());
			req.setOtp(user.getOtp());

			String msg = emailService.verifyOtp(req);
			user.setMsg(msg);
			return ResponseEntity.ok(user);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	


	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/hello")
	public String hello() {
		return "hello2";

	}
}
