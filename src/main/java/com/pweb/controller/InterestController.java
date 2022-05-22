package com.pweb.controller;

import com.pweb.dao.Interest;
import com.pweb.dao.User;
import com.pweb.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/interest")
public class InterestController {
    @Autowired
    InterestService interestService;

    @GetMapping("/all")
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(interestService.findAll());
    }

    @GetMapping
    public ResponseEntity getById(@RequestParam(name = "interestId") int interestId) {
        return ResponseEntity.status(HttpStatus.OK).body(interestService.getById(interestId));
    }

    @PostMapping
    public ResponseEntity createInterest(@RequestBody Interest interest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(interestService.save(interest));
    }

    @DeleteMapping
    public ResponseEntity deleteInterest(@RequestParam(name = "interestId") int interestId) {
        return ResponseEntity.status(HttpStatus.OK).body(interestService.delete(interestId));
    }

    @GetMapping("/email")
    public ResponseEntity getInterestByUserEmail(@RequestParam(name="userEmail") String userEmail,
                                      @RequestParam(name = "offerId") int offerId) {
        return ResponseEntity.status(HttpStatus.OK).body(interestService.getInterest(userEmail, offerId));
    }
}
