package com.pweb.utils;

import com.pweb.config.RabbitMQConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {
    Logger logger = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(value = RabbitMQConfiguration.TOPIC_EXCHANGE_NAME, type = "topic"),
                                            key = "", value = @Queue( RabbitMQConfiguration.QUEUE_NAME)))
    public void receiveMessage(String message) {
        logger.info("Received [" + message + "]");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}