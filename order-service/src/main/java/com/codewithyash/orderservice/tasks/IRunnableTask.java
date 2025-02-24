package com.codewithyash.orderservice.tasks;

import com.codewithyash.basedomain.dto.OrderEvent;
import com.codewithyash.basedomain.dto.OrderEventResponse;

import java.util.concurrent.CompletableFuture;

public interface IRunnableTask {
    CompletableFuture<?> execute(OrderEvent orderEvent, OrderEventResponse orderEventResponse);

}
