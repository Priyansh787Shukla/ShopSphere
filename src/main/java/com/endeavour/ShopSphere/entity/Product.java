package com.endeavour.ShopSphere.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="products")
@Entity
public class Product
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable=false)
    private String name;

    @NotNull
    @Positive
    @Column(nullable=false)
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    @Column(nullable=false)
    private Integer stock;

    private String description;

    @ManyToOne
    @JoinColumn(name="category_id",  nullable=false)
    private Category category;
}
