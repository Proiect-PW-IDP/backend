package com.pweb.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="offer")
@Data
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="category")
    String category;
    @Column(name="user_id")
    int userId;
    @Column(name="title")
    String title;
    @Column(name="details")
    String details;
    @Column(name="provided")
    Boolean provided;
    @Column(name="image")
    String image;
}
