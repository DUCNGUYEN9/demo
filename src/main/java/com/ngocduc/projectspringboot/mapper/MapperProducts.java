package com.ngocduc.projectspringboot.mapper;

import com.ngocduc.projectspringboot.model.dto.request.ProductRequest;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.model.entity.Category;
import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MapperProducts implements  MapperGeneric<Products, ProductRequest, ProductResponse>{
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Products mapperRequestToEntity(ProductRequest productRequest) {

        return Products.builder().productName(productRequest.getProduct_name())
                .sku(Products.generateUniqueCode())
                .description(productRequest.getDescription())
                .unit_price(productRequest.getUnit_price())
                .stock_quantity(productRequest.getStock_quantity())
                .image(productRequest.getImage())
                .create_at(new Date())
                .category(new Category(productRequest.getCategory()) )
                .build();
    }

    @Override
    public ProductResponse mapperEntityToResponse(Products products) {
        return ProductResponse.builder().product_name(products.getProductName())
                .sku(products.getSku())
                .description(products.getDescription())
                .unit_price(products.getUnit_price())
                .catalogName(products.getCategory().getCategoryName())
                .stock_quantity(products.getStock_quantity())
                .image(products.getImage())
                .build();
    }
}
