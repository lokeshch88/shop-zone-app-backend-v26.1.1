package com.shopzone.app.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shopzone.app.entity.Merchant;
import com.shopzone.app.entity.User;
import com.shopzone.app.repo.UserRepo;

@Service
public class AuthenticationService {
	
	private final UserRepo userRepo;
	
	public AuthenticationService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}	
	
	 public User getCurrentUser() {
	        Authentication authentication =
	                SecurityContextHolder
	                        .getContext()
	                        .getAuthentication();

	        String username = authentication.getName();

	        return userRepo.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException( "Authenticated user not found" ));
	    }

	    public Merchant getCurrentMerchant() {

	        User user = getCurrentUser();
	        return user.getMerchant();
	    }

	    public Long getCurrentMerchantId() {
	        return getCurrentMerchant().getId();
	    }

}
