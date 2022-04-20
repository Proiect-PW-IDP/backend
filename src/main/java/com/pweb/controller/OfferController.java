package com.pweb.controller;

import com.pweb.dao.Offer;
import com.pweb.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/offer")
public class OfferController {
    @Autowired
    OfferService offerService;

    @GetMapping("/all")
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(offerService.findAll());
    }

    @GetMapping
    public ResponseEntity getById(@RequestParam(name = "offerId") int offerId) {
        return ResponseEntity.status(HttpStatus.OK).body(offerService.getById(offerId));
    }

    @PostMapping
    public ResponseEntity createOffer(@RequestBody Offer offer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(offerService.save(offer));
    }

    @DeleteMapping
    public ResponseEntity deleteOffer(@RequestParam(name = "offerId") int offerId) {
        return ResponseEntity.status(HttpStatus.OK).body(offerService.delete(offerId));
    }
}