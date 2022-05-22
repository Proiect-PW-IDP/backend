package com.pweb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pweb.dto.InterestDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingkey;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LogManager.getLogger(RabbitMqSender.class.toString());


    public void send(InterestDTO interestDTO) {
        rabbitTemplate.convertAndSend(exchange, "offer.test", interestDTO);
        logger.info("messahe sent");
    }

}
