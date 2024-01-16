package com.hieuduy.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private String fullName;
    private String contactNumber;

    private Date date;
    private String status;
    private String note;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", foreignKey = @ForeignKey(name = "fk_order_customer_id"))
    private Customer customer;

    public float getTotalPrice() {
        if (orderDetails == null || orderDetails.isEmpty()) return 0f;
        float ret = 0f;
        for (OrderDetail od : orderDetails) {
            ret += od.getQuantity() * od.getUnitPrice();
        }
        return ret;
    }
}
