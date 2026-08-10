package com.endeavour.ShopSphere.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_items")
@Entity
public class CartItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "cart_id", nullable = false)
    @ManyToOne
    private Cart cart;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne
    private Product product;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantity;
}
