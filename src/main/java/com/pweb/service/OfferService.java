package com.pweb.service;

import com.pweb.dao.Offer;
import com.pweb.repository.OfferRepository;
import com.pweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {
    @Autowired
    OfferRepository offerRepository;

    @Autowired
    UserRepository userRepository;

    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    public Offer getById(int id) {
        return offerRepository.findById(id).get();
    }

    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    public Offer saveByUserEmail(Offer offer, String email) {
        offer.setUserId(userRepository.findByEmail(email).get().getId());
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
}
