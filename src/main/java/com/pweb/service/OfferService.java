package com.pweb.service;

import com.pweb.dao.Offer;
import com.pweb.repository.OfferRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class OfferService {
    private OfferRepository offerRepository;

    private List<Offer> offerCounter = null;
    private Counter offerRequestsCounter = null;

    public OfferService(OfferRepository offerRepository, CompositeMeterRegistry meterRegistry) {
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
}
