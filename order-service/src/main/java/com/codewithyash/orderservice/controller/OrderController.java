package com.codewithyash.orderservice.controller;

import com.codewithyash.basedomain.dto.Order;
import com.codewithyash.basedomain.dto.OrderEvent;
import com.codewithyash.orderservice.service.TaskExecutorService;
import com.codewithyash.orderservice.utility.TaskEnum;
import jakarta.annotation.PreDestroy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final TaskExecutorService taskExecutorService;

    public OrderController(TaskExecutorService taskExecutorService) {
        this.taskExecutorService = taskExecutorService;
    }

    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {

        List<TaskEnum> task = Arrays.asList(TaskEnum.VALIDATEINPUTPARAMETERSREQUEST,TaskEnum.SENDNEWORDEREVENT);
        taskExecutorService.executeTask(task,getNewOrderEvent(order));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Order Processed Successfully");
    }



    @PostMapping("/cancel-order")
    public ResponseEntity<String> cancelOrder(@RequestBody Order order) {

        List<TaskEnum> task = Arrays.asList(TaskEnum.VALIDATEINPUTPARAMETERSREQUEST,TaskEnum.VALIDATEINPUTPARAMETERSREQUEST);
        taskExecutorService.executeTask(task,getCancelOrderEvent(order));

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

//    @PreDestroy
//    public void destroyExecutor() {
//        taskExecutor.shutdown();
//        System.out.println("ThreadPoolTaskExecutor shutdown gracefully.");
//    }


}

