package com.shopzone.app.controller;

import com.shopzone.app.dto.DeliveryResponseDto;
import com.shopzone.app.entity.Delivery;
import com.shopzone.app.entity.OrderStatus;
import com.shopzone.app.service.DeliveryService;

import jdk.internal.org.jline.utils.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
    
    private static final Logger log = LoggerFactory.getLogger(DeliveryController.class);
    

    // ✅ ADMIN: Assign delivery boy
    @PostMapping("/assign")
    public ResponseEntity<Delivery> assignDelivery(
            @RequestParam String orderId,
            @RequestParam Long deliveryBoyId
    ) {
        return ResponseEntity.ok(
                deliveryService.assignDeliveryBoy(orderId, deliveryBoyId)
        );
    }

    // ✅ DELIVERY BOY: My deliveries
//    @GetMapping("/my")
//    public ResponseEntity<List<Delivery>> getMyDeliveries() {
//        return ResponseEntity.ok(deliveryService.getMyDeliveries());
//    }
    @GetMapping("/my")
    public ResponseEntity<List<DeliveryResponseDto>> myDeliveries(Authentication authentication) {

        String username = authentication.getName(); // SAFE
      log.info("delivery for username fetched "+username);
        return ResponseEntity.ok(
                deliveryService.getMyDeliveries(username)
        );
    }


    // ✅ DELIVERY BOY: Update delivery status
    @PatchMapping("/{deliveryId}/status")
    public ResponseEntity<DeliveryResponseDto> updateStatus(
            @PathVariable Long deliveryId,
            @RequestParam OrderStatus status,@RequestParam String orderId
    ) {
        return ResponseEntity.ok(
                deliveryService.updateDeliveryStatus(deliveryId, status, orderId)
        );
    }
}

