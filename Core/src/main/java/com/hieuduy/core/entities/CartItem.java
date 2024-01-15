package com.hieuduy.core.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "shopping_cart_id", foreignKey = @ForeignKey(name = "fk_cart_item_shopping_cart_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ShoppingCart shoppingCart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", foreignKey = @ForeignKey(name = "fk_cart_item_product_id"))
    private Product product;


}
