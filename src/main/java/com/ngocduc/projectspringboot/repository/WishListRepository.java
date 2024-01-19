package com.ngocduc.projectspringboot.repository;

import com.ngocduc.projectspringboot.model.dto.response.WishListResponse;
import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.model.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Integer> {
    @Query("select p from Products p where p.product_id in (select w.products.product_id from WishList w where w.users.id = :userId)")
    List<Products> getAllWishList(long userId);
    @Query("select w from WishList w where w.products.product_id = :productId and w.users.id = :userId")
    WishList findByProducts(long productId,long userId);

    @Query("select p from Products p where p.product_id in (select w.products.product_id from WishList w )")
    List<Products> getAllWishLists();

}
