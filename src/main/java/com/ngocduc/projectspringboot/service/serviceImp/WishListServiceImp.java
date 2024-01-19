package com.ngocduc.projectspringboot.service.serviceImp;

import com.ngocduc.projectspringboot.mapper.MapperProducts;
import com.ngocduc.projectspringboot.mapper.MapperWishList;
import com.ngocduc.projectspringboot.model.dto.request.WishListRequest;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.model.dto.response.WishListResponse;
import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.model.entity.Users;
import com.ngocduc.projectspringboot.model.entity.WishList;
import com.ngocduc.projectspringboot.repository.ProductRepository;
import com.ngocduc.projectspringboot.repository.UsersRepository;
import com.ngocduc.projectspringboot.repository.WishListRepository;
import com.ngocduc.projectspringboot.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishListServiceImp implements WishListService {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private MapperWishList mapperWishList;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MapperProducts mapperProducts;

    @Override
    public WishListResponse addNew(long userId, Products products) {
        Users users = usersRepository.findById(userId).get();
        List<Products> productsList = new ArrayList<>();
        productsList.add(products);
        WishList wishList= new WishList();
        wishList.setUsers(users);
        wishList.setProducts(products);
        return mapperWishList.mapperEntityToResponse(wishListRepository.save(wishList));
    }

    @Override
    public List<ProductResponse> getAllWishList(long userId) {
        List<Products> wishListList = wishListRepository.getAllWishList(userId);

        List<ProductResponse> productResponseList = wishListList.stream()
                .map(products -> mapperProducts.mapperEntityToResponse(products))
                .collect(Collectors.toList());
        return productResponseList;
    }

    @Override
    public boolean deleteWishList(long productId,long userId) {
        WishList wishList = wishListRepository.findByProducts(productId,userId);
        if (wishList != null){
            wishListRepository.delete(wishList);
            return true;
        }
        return false;
    }

    @Override
    public boolean isExist(long productId, long userId) {
        WishList wishList = wishListRepository.findByProducts(productId,userId);
        return wishList != null;
    }

    @Override
    public List<ProductResponse> getAllWishLists() {
        List<Products> productsList = wishListRepository.getAllWishLists();

        List<ProductResponse> productResponseList = productsList.stream()
                .map(products -> mapperProducts.mapperEntityToResponse(products))
                .collect(Collectors.toList());
        return productResponseList;
    }
}
