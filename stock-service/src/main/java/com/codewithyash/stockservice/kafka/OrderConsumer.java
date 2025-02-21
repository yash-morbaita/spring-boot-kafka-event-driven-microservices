package com.codewithyash.stockservice.kafka;

import com.codewithyash.basedomain.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}" )
    public void consume(OrderEvent event) {

        logger.info(String.format("Order event received in stock service => %s", event.toString()));

        if("NEW_ORDER".equals(event.getStatus())) {
            logger.info("NEW ORDER is Placed");
        } else if ("CANCEL_ORDER".equals(event.getStatus())) {
            logger.info("ORDER is cancelled");
        }

        //save the event data into database and do more operations
    }
}
