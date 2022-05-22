package com.pweb.service;

import com.pweb.dao.Offer;
import com.pweb.dto.InterestDTO;
import com.pweb.repository.OfferRepository;
import com.pweb.repository.UserRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService /*implements CommandLineRunner*/ {
    private OfferRepository offerRepository;


    @Autowired
    private RabbitMqSender rabbitMqSender;

    Logger logger = LoggerFactory.getLogger(OfferService.class);

    private List<Offer> offerCounter = null;
    private Counter offerRequestsCounter = null;

    public OfferService(OfferRepository offerRepository, CompositeMeterRegistry meterRegistry/*, RabbitTemplate rabbitTemplate, Receiver receiver*/) {
        this.offerRepository = offerRepository;
        offerRequestsCounter = meterRegistry.counter("offer_requests");
        offerCounter = meterRegistry.gaugeCollectionSize("offers", Tags.empty(), this.offerRepository.findAll());
    }

    @Autowired
    UserRepository userRepository;

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

    public Offer saveByUserEmail(Offer offer, String email) {
        offer.setUserId(userRepository.findByEmail(email).get().getId());
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

    public List<Offer> findAllProvidedByCategoryName(String categoryName, String userEmail) {
        return offerRepository.findAll()
                .stream()
                .filter(offer -> offer.getProvided() && offer.getCategory().equals(categoryName) &&
                        offer.getUserId() != userRepository.findByEmail(userEmail).get().getId())
                .collect(Collectors.toList());
    }

    public List<Offer> findAllRequired() {
        return offerRepository.findAll()
                .stream()
                .filter(offer -> !offer.getProvided())
                .collect(Collectors.toList());
    }

    public List<Offer> findAllRequiredByCategoryName(String categoryName, String userEmail) {
        return offerRepository.findAll()
                .stream()
                .filter(offer -> !offer.getProvided() && offer.getCategory().equals(categoryName) &&
                        offer.getUserId() != userRepository.findByEmail(userEmail).get().getId())
                .collect(Collectors.toList());
    }

    public List<Offer> findAllOffersByUserEmail(String userEmail) {
        return offerRepository.findAll()
                .stream()
                .filter(offer -> offer.getUserId() == userRepository.findByEmail(userEmail).get().getId())
                .collect(Collectors.toList());
    }

    public InterestDTO createAndSendInterestMessage(InterestDTO interestDTO) {
        rabbitMqSender.send(interestDTO);
        logger.info("message sent ");
        logger.info(interestDTO.toString());

        return interestDTO;
    }

}
