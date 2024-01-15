package com.hieuduy.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String description;
    private int currentQuantity;
    @NotNull(message = "Price is mandatory")
    private float price;
    private float sale; // %

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "product_image",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_model_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ImageModel> images;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id",
            foreignKey = @ForeignKey(name = "fk_product_category_id"))
    @NotNull(message = "Category is mandatory")
    private Category category;
    private boolean isActivated;

    public float getSalePrice(){
        return this.price * (100 - this.sale) / 100;
    }
}
