package com.hieuduy.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String address;
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_roles"
            , joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", foreignKey = @ForeignKey(name = "fk_customer_role_id"))
            , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id", foreignKey = @ForeignKey(name = "fk_role_customer_id")))
    private List<Role> roles;

    @OneToOne(mappedBy = "customer")
    private ShoppingCart shoppingCart;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}

