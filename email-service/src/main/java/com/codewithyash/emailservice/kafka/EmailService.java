package com.codewithyash.emailservice.kafka;

import com.codewithyash.basedomain.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void sendEmail(OrderEvent event) {

        logger.info(String.format("Order event received in email service => %s", event));
        if("NEW_ORDER".equals(event.getStatus())) {
            logger.info("Send new order Email");
        } else if ("CANCEL_ORDER".equals(event.getStatus())) {
            logger.info("Send Cancel Order Email");
        }

        //send email logic

    }
}
