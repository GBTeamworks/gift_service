package com.giftservice.gift_service.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String userName;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "password")
    private String userPassword;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Gift> gifts;
}
