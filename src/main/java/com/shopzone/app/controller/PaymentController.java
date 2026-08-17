package com.shopzone.app.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.shopzone.app.dto.PaymentDto;
import com.shopzone.app.entity.OrderStatus;
import com.shopzone.app.repo.OrderRepository;
import com.razorpay.Order;

import com.shopzone.utils.RandomCodeUtil;

import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

@RestController
@CrossOrigin("*")
@RequestMapping("/payments")
public class PaymentController {
	
	  @Value("${razorpay.key_id}")
	    private String keyId;

	    @Value("${razorpay.key_secret}")
	    private String keySecret;

	    @Autowired
		private OrderRepository orderRepository;

	    @PostMapping("/create-order")
	    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) throws RazorpayException {
	        RazorpayClient client = new RazorpayClient(keyId, keySecret);

	        JSONObject orderRequest = new JSONObject();
	        int amount = Integer.parseInt(data.get("amount").toString()); // Amount in paise
	        orderRequest.put("amount", amount);
	        orderRequest.put("currency", "INR");
	        orderRequest.put("receipt", "order_rcptid_11");

	        Order order = client.orders.create(orderRequest);

	        Map<String, Object> response = new HashMap<>();
	        response.put("orderId", order.get("id"));
	        response.put("amount", order.get("amount"));
	        response.put("currency", order.get("currency"));
	        return ResponseEntity.ok(response);
	    }

	    @PostMapping("/verify")
	    public ResponseEntity<String> verifyPayment(@RequestBody Map<String, String> data) {
	        String orderId = data.get("razorpay_order_id");
	        String paymentId = data.get("razorpay_payment_id");
	        String signature = data.get("razorpay_signature");
	        String id=data.get("id");

	        try {
	        	JSONObject options = new JSONObject();
	        	options.put("razorpay_order_id", orderId);
	        	options.put("razorpay_payment_id", paymentId);
	        	options.put("razorpay_signature", signature);

	        	boolean isValid = Utils.verifyPaymentSignature(options, keySecret);

	            if (isValid) {
	                // ✅ Verified → Update order status to CONFIRMED
	            	com.shopzone.app.entity.Order order = orderRepository.findByOrderId(id)
	                              .orElseThrow(() -> new RuntimeException("Order not found"));

	                order.setStatus(OrderStatus.CONFIRMED);
//	                order.setRazorpayPaymentId(paymentId);
	                orderRepository.save(order);

	                return ResponseEntity.ok("Payment verified");
	            } else {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Verification failed: " + e.getMessage());
	        }
	    }

	    private String hmacSha256(String data, String secret) throws Exception {
	        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
	        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
	        sha256_HMAC.init(secretKey);
	        byte[] hash = sha256_HMAC.doFinal(data.getBytes());
	        return Base64.getEncoder().encodeToString(hash);
	    }

	    
	    
	@PostMapping("/process")
	public ResponseEntity<?> processPayment(PaymentDto paymentDto){
		try {
			System.out.println("In paytemenet controller method");
//			paymentDto.setPaymentStatus("SUCCESS");
			paymentDto.setPaymentStatus("FAILED");
			String txnId= RandomCodeUtil.generateTransactionId();
			paymentDto.setTxnId(txnId);
			return new ResponseEntity<>(paymentDto, HttpStatus.OK);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		
		
		
		
	}
}
