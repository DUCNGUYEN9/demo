package com.ngocduc.projectspringboot.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartResponse {
    private long shoppingCartId;
    private String product_name;
    private String sku;
    private String description;
    private BigDecimal unit_price;
    private String image;
    private int orderQuantity;
}
