package com.ngocduc.projectspringboot.mapper;

import com.ngocduc.projectspringboot.model.dto.request.WishListRequest;
import com.ngocduc.projectspringboot.model.dto.response.WishListResponse;
import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.model.entity.Users;
import com.ngocduc.projectspringboot.model.entity.WishList;
import com.ngocduc.projectspringboot.repository.ProductRepository;
import com.ngocduc.projectspringboot.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperWishList implements  MapperGeneric<WishList, WishListRequest, WishListResponse>{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public WishList mapperRequestToEntity(WishListRequest wishListRequest) {
        Users users = usersRepository.findById(wishListRequest.getUsers()).get();
        //
        Products products = productRepository.findById(wishListRequest.getProducts()).get();
//        List<Products> productsList = new ArrayList<>();
//        productsList.add(products);

        return WishList.builder()
                .users(users)
                .products(products)
                .build();
    }

    @Override
    public WishListResponse mapperEntityToResponse(WishList wishList) {
        Products products = wishList.getProducts();
        List<Products> productsList = new ArrayList<>();
        productsList.add(products);
        return WishListResponse.builder()
                .products(productsList)
                .build();
    }
}
