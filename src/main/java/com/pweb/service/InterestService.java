package com.pweb.service;

import com.pweb.dao.Interest;
import com.pweb.dao.Offer;
import com.pweb.repository.InterestRepository;
import com.pweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterestService {
    @Autowired
    InterestRepository interestRepository;

    @Autowired
    UserRepository userRepository;

    public List<Interest> findAll() {
        return interestRepository.findAll();
    }

    public Interest getById(int id) {
        return interestRepository.findById(id).get();
    }

    public Interest save(Interest interest) {
        Optional<Interest> optionalInterest = interestRepository.getInterest(interest.getUserId(), interest.getOfferId());

        if (!optionalInterest.isPresent()) {
            return interestRepository.save(interest);
        } else {
            return interest;
        }
    }

    public Interest delete(int interestId) {
        Interest interest = getById(interestId);
        interestRepository.delete(interest);
        return interest;
    }

    public Interest getInterest(String userEmail, int offerId) {
        return interestRepository.getInterest(userRepository.findByEmail(userEmail).get().getId(), offerId).orElse(null);
    }
}
