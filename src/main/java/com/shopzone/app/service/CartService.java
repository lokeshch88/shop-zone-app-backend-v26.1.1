package com.shopzone.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopzone.app.dto.CartDto;
import com.shopzone.app.entity.Cart;
import com.shopzone.app.entity.CartItem;
//import com.shopzone.app.repo.CartRepo;
import com.shopzone.app.repo.CartRepo;

@Service
public class CartService {

	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	public void saveCart(Long id,CartDto cartDto) {
		try {
		// 1) Map base cart fields
        Cart cart = modelMapper.map(cartDto, Cart.class);
        cart.setUserId(id);

        // 2) Map each CartItemDto into a CartItem entity and hook it up
        List<CartItem> items = cartDto.getItems().stream()
            .map(itemDto -> {
                // map basic fields
                CartItem item = modelMapper.map(itemDto, CartItem.class);
                // set back-reference to the parent Cart
                item.setCart(cart);
                return item;
            })
            .collect(Collectors.toList());

        // 3) Associate the list of items with the cart
        cart.setItems(items);

        // 4) (Optionally) compute & set totalAmount on the Cart entity
//        double total = items.stream()
//            .mapToDouble(i -> i.getPrice() * i.getQuantity())
//            .sum();
//        cart.setTotalAmount(total);

        // 5) Persist
        cartRepo.save(cart);
		}catch (Exception e) {
		e.printStackTrace();
		}
		
	}

}
