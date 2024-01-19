package com.hieuduy.core.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", foreignKey = @ForeignKey(name = "fk_shopping_cart_customer_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "shoppingCart")
    private List<CartItem> cartItems;


    public int getAmountItem() {
        return (cartItems == null || cartItems.size() == 0) ? 0 : cartItems.size();
    }

    public float getTotalPrice() {
        if (cartItems == null || cartItems.size() == 0) return 0f;
        float ret = 0f;
        for (CartItem ci : cartItems) {
            if (ci.getProduct().getSale() > 0f) {
                ret += ci.getQuantity() * ci.getProduct().getSalePrice();
            } else {
                ret += ci.getQuantity() * ci.getProduct().getPrice();
            }
        }
        return ret;
    }


}
