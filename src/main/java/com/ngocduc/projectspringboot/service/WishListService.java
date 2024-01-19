package com.ngocduc.projectspringboot.service;

import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.model.dto.response.WishListResponse;
import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.model.entity.WishList;

import java.util.List;

public interface WishListService {
    WishListResponse addNew(long userId, Products products);
    List<ProductResponse> getAllWishList(long userId);
    boolean deleteWishList(long productId,long userId);
    boolean isExist(long productId,long userId);
    List<ProductResponse> getAllWishLists();
}
