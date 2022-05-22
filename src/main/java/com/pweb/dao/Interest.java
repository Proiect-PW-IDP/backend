package com.pweb.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="interest")
@Data
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="user_id")
    int userId;
    @Column(name="offer_id")
    int offerId;
}
