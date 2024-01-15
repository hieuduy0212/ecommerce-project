package com.hieuduy.core.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_details")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    private float unitPrice;

    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", foreignKey = @ForeignKey(name = "fk_order_detail_product_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", foreignKey = @ForeignKey(name = "fk_order_detail_order_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Order order;
}
