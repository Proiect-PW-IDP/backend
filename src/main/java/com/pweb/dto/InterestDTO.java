package com.pweb.dto;

import java.io.Serializable;

public class InterestDTO implements Serializable {
    private String senderEmail;
    private String userOfferEmail;
    private String message;

    public InterestDTO() {
    }

    public InterestDTO(String senderEmail, String userOfferEmail, String message) {
        this.senderEmail = senderEmail;
        this.userOfferEmail = userOfferEmail;
        this.message = message;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public InterestDTO setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
        return this;
    }

    public String getUserOfferEmail() {
        return userOfferEmail;
    }

    public InterestDTO setUserOfferEmail(String userOfferEmail) {
        this.userOfferEmail = userOfferEmail;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public InterestDTO setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "InterestDTO{" +
                "senderEmail='" + senderEmail + '\'' +
                ", userOfferEmail='" + userOfferEmail + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
