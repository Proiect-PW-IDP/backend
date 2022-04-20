package com.pweb.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="username")
    String username;
    @Column(name="password")
    String password;
    @Column(name="email")
    String email;
    @Column(name="name")
    String name;
    @Column(name="telephone")
    String telephone;
    @Column(name="address")
    String address;
}
