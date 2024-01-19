package com.ngocduc.projectspringboot.mapper;

import com.ngocduc.projectspringboot.model.dto.request.ProductRequest;
import com.ngocduc.projectspringboot.model.dto.request.ShoppingCartRequest;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.model.dto.response.ShoppingCartResponse;
import com.ngocduc.projectspringboot.model.entity.Category;
import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.model.entity.ShoppingCart;
import com.ngocduc.projectspringboot.repository.CategoryRepository;
import com.ngocduc.projectspringboot.repository.ProductRepository;
import com.ngocduc.projectspringboot.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MapperCart implements  MapperGeneric<ShoppingCart, ShoppingCartRequest, ShoppingCartResponse>{
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public ShoppingCart mapperRequestToEntity(ShoppingCartRequest shoppingCartRequest) {
        return null;
    }

    @Override
    public ShoppingCartResponse mapperEntityToResponse(ShoppingCart shoppingCart) {
        Products products = productRepository.findById(shoppingCart.getProducts().getProduct_id()).get();
        return ShoppingCartResponse.builder()
                .shoppingCartId(shoppingCart.getShopping_cart_id())
                .product_name(shoppingCart.getProducts().getProductName())
                .sku(products.getSku())
                .description(products.getDescription())
                .unit_price(products.getUnit_price())
                .image(products.getImage())
                .orderQuantity(shoppingCart.getOrder_quantity())
                .build();
    }
}
