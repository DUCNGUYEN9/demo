package com.ngocduc.projectspringboot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Order_Detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;
    private String name;
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal unit_price;
    @Column(columnDefinition = "int default 0 check (order_quantity >= 0)")
    private int order_quantity;
}
