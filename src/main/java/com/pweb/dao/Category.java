package com.pweb.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="category")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="name")
    String name;
    @Column(name="description")
    String description;
    @Column(name="image")
    String image;
}
