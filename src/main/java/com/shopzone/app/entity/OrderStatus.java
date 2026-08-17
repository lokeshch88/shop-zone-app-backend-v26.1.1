package com.shopzone.app.entity;

public enum OrderStatus {
    PENDING, //Order received but not yet processed
    CONFIRMED, //Order confirmed by seller/inventory checked
    CANCELLED, //Order was cancelled before shipping
    COMPLETED,
    PROCESSING,	//Order is being prepared/packed
    SHIPPED,	//Order has left the warehouse and is in transit
    DELIVERED,	//Order delivered to the customer
    RETURNED,  //Customer returned the order
    REFUNDED,	//Customer was refunded (partial or full)
    FAILED,	 //Order payment failed or order could not be processed
    ON_HOLD,	//Order is temporarily on hold (e.g., awaiting info)
    OUT_FOR_DELIVERY, ASSIGNED,
}
