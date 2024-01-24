package com.hieuduy.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private ShoppingCart shoppingCart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", foreignKey = @ForeignKey(name = "fk_cart_item_product_id"))
//    @ToString.Exclude
    @EqualsAndHashCode.Exclude
//    @JsonIgnore
    private Product product;

    public float getTotalPrice(){
        if(this.product.getSale() > 0f){
            return this.quantity * this.product.getSalePrice();
        }else{
            return this.quantity * this.product.getPrice();
        }
    }

}
