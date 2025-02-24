package com.codewithyash.orderservice.tasks;

import com.codewithyash.basedomain.dto.OrderEvent;
import com.codewithyash.basedomain.dto.OrderEventResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

@Component
public class ValidateInputParametersRequestTask implements IRunnableTask{

    private static final Logger mainLogger = LoggerFactory.getLogger(ValidateInputParametersRequestTask.class);
    @Override
    public CompletableFuture<?> execute(OrderEvent orderEvent, OrderEventResponse orderEventResponse) {
        StringJoiner logger = new StringJoiner("|");
        logger.add("Starting ValidateInputParametersRequestTask ");
        if(orderEvent.getOrder() == null) {
            logger.add("Order history is null");
            throw new IllegalArgumentException("Order history is null");
        }
        if(orderEvent.getStatus() == null) {
            logger.add("Order Event status is null");
            throw new RuntimeException("Status not Found in Order History");
        }
        if(orderEvent.getMessage() == null) {
            logger.add("Order Event message is null");
            throw new RuntimeException("Order Message not Found in Order History");
        }
        logger.add("ValidateInputParametersRequestTask End");

        mainLogger.info(logger.toString());
        return CompletableFuture.completedFuture("Task ValidateInputParametersRequestTask completed");

    }
}
