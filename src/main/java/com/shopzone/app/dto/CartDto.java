package com.shopzone.app.dto;

import java.util.List;

import com.shopzone.app.entity.CartItem;

public class CartDto {
	
    private List<CartItem> items;

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	
	
	

}
