package com.codewithyash.orderservice.tasks;

import com.codewithyash.basedomain.dto.OrderEvent;
import com.codewithyash.basedomain.dto.OrderEventResponse;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

@Component
public class SendNewOrderEventTask implements IRunnableTask{

    private static final Logger logger = LoggerFactory.getLogger(SendNewOrderEventTask.class);

    private final NewTopic topic;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public SendNewOrderEventTask(NewTopic topic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public CompletableFuture<?> execute(OrderEvent orderEvent, OrderEventResponse orderEventResponse) {
        StringJoiner stringJoiner = new StringJoiner("|");
        stringJoiner.add("Starting SendNewOrderEventTask");
        stringJoiner.add(String.format("Order event => %s", orderEvent.toString()));
        Message<OrderEvent> message = MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC,topic.name())
                .build();
        kafkaTemplate.send(message);
        stringJoiner.add("Ending SendNewOrderEventTask");


        logger.info(stringJoiner.toString());
        return CompletableFuture.completedFuture("Task SendNewOrderEventTask - Done");

    }
}
