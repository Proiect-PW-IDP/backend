package com.pweb.controller;

import com.pweb.dao.Offer;
import com.pweb.dto.ResponseDTO;
import com.pweb.service.OfferService;
import com.pweb.service.RabbitMqSender;
import com.pweb.utils.Constants;
import com.pweb.utils.Metrics;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
//import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
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

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @GetMapping("/all")
    @Timed("offers.api")
    public ResponseEntity findAll() {

//        Metrics.offerCounter.increment();
//       Metrics.COUNTER.inc();
//        Histogram.Timer requestTimer = Metrics.COMMAND_LATENCY.startTimer();
//        try {
            return ResponseEntity.status(HttpStatus.OK).body(offerService.findAll());
     /*   } finally {
            // Stop the histogram timer
            requestTimer.observeDuration();
        }*/
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

    @GetMapping("/provided/category/sender")
    public ResponseEntity findAllProvidedByCategoryName() {
        rabbitMqSender.send("berge bai");
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
