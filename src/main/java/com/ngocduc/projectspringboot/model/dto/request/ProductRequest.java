package com.ngocduc.projectspringboot.model.dto.request;

import com.ngocduc.projectspringboot.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String product_name;
    private String sku;
    private String description;
    private BigDecimal unit_price;
    private int stock_quantity;
    private String image;
    private Date create_at;
    private long category;
}
