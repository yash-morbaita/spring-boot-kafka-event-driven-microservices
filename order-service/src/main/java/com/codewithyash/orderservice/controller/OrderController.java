package com.codewithyash.orderservice.controller;

import com.codewithyash.basedomain.dto.Order;
import com.codewithyash.basedomain.dto.OrderEvent;
import com.codewithyash.orderservice.kafka.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    @Autowired
    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {

        orderProducer.sendMessage(getNewOrderEvent(order));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Order Processed Successfully");
    }



    @PostMapping("/cancel-order")
    public ResponseEntity<String> cancelOrder(@RequestBody Order order) {

        orderProducer.sendMessage(getCancelOrderEvent(order));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Order Cancelled Successfully");
    }

    private OrderEvent getNewOrderEvent(Order order) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("NEW_ORDER");
        orderEvent.setMessage("Order is in pending state");
        order.setOrderId(UUID.randomUUID().toString());
        orderEvent.setOrder(order);
        return orderEvent;

    }

    private OrderEvent getCancelOrderEvent(Order order) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("CANCEL_ORDER");
        orderEvent.setMessage("Order is Cancelled");
        order.setOrderId(UUID.randomUUID().toString());
        orderEvent.setOrder(order);
        return orderEvent;

    }


}

