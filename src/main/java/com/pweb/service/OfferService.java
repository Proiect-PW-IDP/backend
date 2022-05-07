package com.pweb.service;

import com.pweb.dao.Offer;
import com.pweb.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {
    @Autowired
    OfferRepository offerRepository;

    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    public Offer getById(int id) {
        return offerRepository.findById(id).get();
    }

    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    public Offer delete(int offerId) {
        Offer offer = getById(offerId);
        offerRepository.delete(offer);
        return offer;
    }

    public List<Offer> findAllProvided() {
        return offerRepository.findAll()
                .stream()
                .filter(offer -> offer.getProvided())
                .collect(Collectors.toList());
    }

    public List<Offer> findAllRequired() {
        return offerRepository.findAll()
                .stream()
                .filter(offer -> !offer.getProvided())
                .collect(Collectors.toList());
    }
}
