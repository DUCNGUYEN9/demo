package com.ngocduc.projectspringboot.service;

import com.ngocduc.projectspringboot.model.dto.request.ShoppingCartRequest;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.model.dto.response.ShoppingCartResponse;
import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.model.entity.ShoppingCart;
import com.ngocduc.projectspringboot.model.entity.Users;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart addToCart(long userId, ShoppingCartRequest shoppingCartRequest);
    boolean isExist(long userId,ShoppingCartRequest shoppingCartRequest);
    boolean checkQuantity(long productId,int quantity);
    List<ShoppingCartResponse> findAll(Users users);
    boolean updateQuantity(long productId, int quantity, long userId);
    void delete(long shoppingCartId);

    void deleteAll(long userId);
}
