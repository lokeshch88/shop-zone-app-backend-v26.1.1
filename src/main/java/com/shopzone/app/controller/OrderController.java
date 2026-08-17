package com.shopzone.app.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopzone.app.dto.OrderRequest;
import com.shopzone.app.dto.OrderResponse;
import com.shopzone.app.entity.Order;
import com.shopzone.app.entity.OrderStatus;
import com.shopzone.app.entity.User;
import com.shopzone.app.repo.UserRepo;
import com.shopzone.app.service.OrderService;



@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {

	@Autowired
    private OrderService OrderService;
	@Autowired 
	private UserRepo userRepo;
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderResponse> createOrder(
            @PathVariable Long userId,
            @RequestBody OrderRequest request) {
        return ResponseEntity.ok(OrderService.createOrder(userId, request));
    }

//    @PatchMapping("/{id}/status")
//    public ResponseEntity<OrderResponse> updateStatus(
//            @PathVariable String id,
//            @RequestParam OrderStatus status) {
//    	log.info("In update order status method for order id: "+id);
//        return ResponseEntity.ok(OrderService.updateOrderStatus(id, status));
//    }
    @PatchMapping
    public ResponseEntity<OrderResponse> updateStatus(@RequestBody Map<String, Object> payload) {
        String orderId = String.valueOf(payload.get("orderId").toString());
        OrderStatus status = OrderStatus.valueOf(payload.get("status").toString());

        Long userId = Long.valueOf(payload.get("userId").toString());

        log.info("In update order status method for order id: " + orderId);
log.info("Update order by id "+userId+ " to status "+status);
        return ResponseEntity.ok(OrderService.updateOrderStatus(orderId, status, userId));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(OrderService.getOrdersForUser(userId));
    }
    
    //to avoid anyone can fetch anyones order by passing id random id
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication){
    try {
    	String username = authentication.getName(); // 
    	log.info("Fetch my order for "+username);
    	log.info("In fetch my order method for user: "+username);
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<OrderResponse> orders = OrderService.getOrdersForUser(user.getId());
        return ResponseEntity.ok(orders);
    }catch (Exception e) {
    	log.error(e.getMessage());
    	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build() ;
		
	}
    }

    @GetMapping("/all")  
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
    	log.info("In fetch all order method for admin: ");
        return ResponseEntity.ok(OrderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String id) {
    	log.info("In fetch order for id: "+id);
        return ResponseEntity.ok(OrderService.getOrderById(id));
    }

    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id, @PathVariable Long userId) {
        OrderService.cancelOrder(id, userId);
        return ResponseEntity.noContent().build();
    }
}

