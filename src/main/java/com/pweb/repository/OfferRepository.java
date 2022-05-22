package com.pweb.repository;

import com.pweb.dao.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface OfferRepository extends JpaRepository<Offer, Integer> {
}
