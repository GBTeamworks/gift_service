package com.giftservice.gift_service.entities;

import com.giftservice.gift_service.entities.security.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "gift")
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Gift(Gift save) {
        this.id = save.getId();
        this.user = save.getUser();
        this.title = save.getTitle();
    }

    public Gift() {
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cart_gift",
            joinColumns = @JoinColumn(name = "gift_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    private Set<Cart> carts;
}
