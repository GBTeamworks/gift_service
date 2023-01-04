package com.giftservice.gift_service.entities;

import com.giftservice.gift_service.entities.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ElementCollection(targetClass = Status.class, fetch = FetchType.EAGER)
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Set<Status> status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cart_gift",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_id"))
    private Set<Gift> gifts = new HashSet<>();

}
