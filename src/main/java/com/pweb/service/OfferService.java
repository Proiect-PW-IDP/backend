package com.pweb.service;

import com.pweb.config.RabbitMQConfiguration;
import com.pweb.dao.Offer;
import com.pweb.repository.OfferRepository;
import com.pweb.utils.Receiver;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class OfferService implements CommandLineRunner {
    private OfferRepository offerRepository;

    Logger logger = LoggerFactory.getLogger(OfferService.class);

    private List<Offer> offerCounter = null;
    private Counter offerRequestsCounter = null;

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public OfferService(OfferRepository offerRepository, CompositeMeterRegistry meterRegistry, RabbitTemplate rabbitTemplate, Receiver receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
        this.offerRepository = offerRepository;
        offerRequestsCounter = meterRegistry.counter("offer_requests");
        offerCounter = meterRegistry.gaugeCollectionSize("offers", Tags.empty(), this.offerRepository.findAll());
    }

    public List<Offer> findAll() {
        offerRequestsCounter.increment();
        return offerRepository.findAll();
    }

    public Offer getById(int id) {
        return offerRepository.findById(id).get();
    }

    public Offer save(Offer offer) {
        offerCounter.add(offer);
        return offerRepository.save(offer);
    }

    public Offer delete(int offerId) {
        Offer offer = getById(offerId);
        offerRepository.delete(offer);
        offerCounter.remove(offer);
        return offer;
    }

    public List<Offer> findAllProvided() {
        return offerRepository.findAll()
                .stream()
                .filter(offer -> offer.getProvided())
                .collect(Collectors.toList());
    }

    public List<Offer> findAllProvidedByCategoryName(String categoryName) {
        return offerRepository.findAll()
                .stream()
                .filter(offer -> offer.getProvided() && offer.getCategory().equals(categoryName))
                .collect(Collectors.toList());
    }

    public List<Offer> findAllRequired() {
        return offerRepository.findAll()
                .stream()
                .filter(offer -> !offer.getProvided())
                .collect(Collectors.toList());
    }

    public List<Offer> findAllRequiredByCategoryName(String categoryName) {
        return offerRepository.findAll()
                .stream()
                .filter(offer -> !offer.getProvided() && offer.getCategory().equals(categoryName))
                .collect(Collectors.toList());
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Sending message...");
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.TOPIC_EXCHANGE_NAME, "offer.test", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
