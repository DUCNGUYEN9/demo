package com.ngocduc.projectspringboot.repository;

import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.model.entity.ShoppingCart;
import com.ngocduc.projectspringboot.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    List<ShoppingCart> findAllByUsersIs(Users users);

    ShoppingCart findByProductsAndUsers(Products products, Users users);

    @Query("select c.order_quantity from ShoppingCart c where  c.users.id = :userId and c.products.product_id = :productId")
    int getOrderQuantity(long productId, long userId);

//    @Query("delete from ShoppingCart s where s.users.id = :userId")
//    void deleteAllByUsers(long userId);
    void deleteByUsersId(Long userId);
}
