package com.shopzone.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopzone.app.entity.Merchant;
import com.shopzone.app.entity.User;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String usernameOrEmail);


	boolean existsByEmailAndMerchant(String email, Merchant merchant);
	boolean existsByUsernameAndMerchant(String username, Merchant merchant);

	List<User> findAllByMerchant(Merchant merchant);
	
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);


}
