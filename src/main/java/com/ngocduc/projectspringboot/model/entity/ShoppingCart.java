package com.ngocduc.projectspringboot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shopping_cart_id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private Users users;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Products products;

    @Column(columnDefinition = "int default 0 check (order_quantity >= 0)")
    private int order_quantity;


}
