package com.pweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pweb.dto.InterestDTO;
import com.pweb.dto.ResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
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

    private static Logger logger = LogManager.getLogger(RabbitMqSender.class.toString());

    public void sentInterestDTO(InterestDTO interestDTO) {
        try {
            String orderJson = objectMapper.writeValueAsString(interestDTO);
            Message message = MessageBuilder
                    .withBody(orderJson.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            rabbitTemplate.convertAndSend(exchange, "offer.test", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void send(InterestDTO interestDTO) {
        rabbitTemplate.convertAndSend(exchange, "offer.test", interestDTO);
        logger.info("messahe sent");
    }

}
