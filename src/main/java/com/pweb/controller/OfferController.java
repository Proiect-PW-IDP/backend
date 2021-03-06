package com.pweb.controller;

import com.pweb.dao.Offer;
import com.pweb.dto.InterestDTO;
import com.pweb.service.OfferService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/offer")
@Timed
public class OfferController {
    @Autowired
    OfferService offerService;


    @GetMapping("/all")
    @Timed("offers.api")
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

    @PostMapping("/email")
    public ResponseEntity createOfferByUserEmail(@RequestBody Offer offer, @RequestParam(name = "userEmail") String userEmail) {
        return ResponseEntity.status(HttpStatus.CREATED).body(offerService.saveByUserEmail(offer, userEmail));
    }

    @DeleteMapping
    public ResponseEntity deleteOffer(@RequestParam(name = "offerId") int offerId) {
        return ResponseEntity.status(HttpStatus.OK).body(offerService.delete(offerId));
    }

    @GetMapping("/provided")
    public ResponseEntity findAllProvided() {
        return ResponseEntity.status(HttpStatus.OK).body(offerService.findAllProvided());
    }

    @GetMapping("/provided/category")
    public ResponseEntity findAllProvidedByCategoryName(@RequestParam(name = "categoryName") String categoryName,
                                                        @RequestParam(name = "userEmail") String userEmail) {
        return ResponseEntity.status(HttpStatus.OK).body(offerService.findAllProvidedByCategoryName(categoryName, userEmail));
    }

    @PostMapping("/provided/category/sender")
    public ResponseEntity findAllProvidedByCategoryName(@RequestBody InterestDTO interestDTO) {
        offerService.createAndSendInterestMessage(interestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("message sent!");
    }

    @GetMapping("/required")
    public ResponseEntity findAllRequired() {
        return ResponseEntity.status(HttpStatus.OK).body(offerService.findAllRequired());
    }

    @GetMapping("/required/category")
    public ResponseEntity findAllRequiredByCategoryName(@RequestParam(name = "categoryName") String categoryName,
                                                        @RequestParam(name = "userEmail") String userEmail) {
        return ResponseEntity.status(HttpStatus.OK).body(offerService.findAllRequiredByCategoryName(categoryName, userEmail));
    }

    @GetMapping("/all/myOffers")
    public ResponseEntity findAllByUserId(@RequestParam(name = "userEmail") String userEmail) {
        return ResponseEntity.status(HttpStatus.OK).body(offerService.findAllOffersByUserEmail(userEmail));
    }
}
