package com.ngocduc.projectspringboot.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;
    @Column(name = "product_name",nullable = false,unique = true,columnDefinition = "varchar(100)")
    private String productName;
    @Column(nullable = false,unique = true,columnDefinition = "varchar(100)")
    private String sku;
    private String description;
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal unit_price;
    @Column(columnDefinition = "int default 0 check (stock_quantity >= 0)")
    private int stock_quantity;
    @Column(columnDefinition = "varchar(255)")
    private String image;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date create_at;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updated_at;
    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    private Category category;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<ShoppingCart> shoppingCart;

    @OneToMany(mappedBy = "products",fetch = FetchType.LAZY)
    private List<WishList> wishLists;
    @OneToMany(mappedBy = "products",cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
    public static String generateUniqueCode() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 7);
    }


}
