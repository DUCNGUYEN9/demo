package com.ngocduc.projectspringboot.service.serviceImp;

import com.ngocduc.projectspringboot.mapper.MapperCart;
import com.ngocduc.projectspringboot.mapper.MapperProducts;
import com.ngocduc.projectspringboot.model.dto.request.ShoppingCartRequest;
import com.ngocduc.projectspringboot.model.dto.response.ShoppingCartResponse;
import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.model.entity.ShoppingCart;
import com.ngocduc.projectspringboot.model.entity.Users;
import com.ngocduc.projectspringboot.repository.ProductRepository;
import com.ngocduc.projectspringboot.repository.ShoppingCartRepository;
import com.ngocduc.projectspringboot.repository.UsersRepository;
import com.ngocduc.projectspringboot.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImp implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UsersRepository usersRepository;


    @Override
    public ShoppingCart addToCart(long userId, ShoppingCartRequest shoppingCartRequest) {

        Products products = productRepository.findById(shoppingCartRequest.getProductId()).get();
        Users users = usersRepository.findById(userId).get();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setProducts(products);
        shoppingCart.setUsers(users);
        shoppingCart.setOrder_quantity(shoppingCartRequest.getQuantity());
        return shoppingCartRepository.save(shoppingCart);

    }

    @Override
    public boolean isExist(long userId, ShoppingCartRequest shoppingCartRequest) {
        Products products = productRepository.findById(shoppingCartRequest.getProductId()).get();
        Users users = usersRepository.findById(userId).get();
        ShoppingCart shoppingCart = shoppingCartRepository.findByProductsAndUsers(products, users);
        return shoppingCart != null;
    }

    @Override
    public boolean checkQuantity(long productId, int quantity) {
        Products products = productRepository.checkQuantity(productId, quantity);
        return products != null;
    }

    @Override
    public List<ShoppingCartResponse> findAll(Users users) {
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAllByUsersIs(users);

        List<ShoppingCartResponse> responseList = new ArrayList<>();

        shoppingCartList.forEach(sc -> {
            ShoppingCartResponse cartResponse = new ShoppingCartResponse();
            cartResponse.setShoppingCartId(sc.getShopping_cart_id());
            cartResponse.setProduct_name(sc.getProducts().getProductName());
            cartResponse.setDescription(sc.getProducts().getDescription());
            cartResponse.setImage(sc.getProducts().getImage());
            cartResponse.setSku(sc.getProducts().getSku());
            cartResponse.setUnit_price(sc.getProducts().getUnit_price());
            cartResponse.setOrderQuantity(shoppingCartRepository
                    .getOrderQuantity(sc.getProducts().getProduct_id(), users.getId()));
            responseList.add(cartResponse);
        });

        return responseList;
    }



    @Override
    public boolean updateQuantity(long productId, int quantity, long userId) {
        Products products = productRepository.findById(productId).get();
        Users users = usersRepository.findById(userId).get();
        ShoppingCart shoppingCart = shoppingCartRepository.findByProductsAndUsers(products,users);
        shoppingCart.setOrder_quantity(quantity);
        shoppingCartRepository.save(shoppingCart);
        return true;
    }

    @Override
    public void delete(long shoppingCartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).get();
       shoppingCartRepository.delete(shoppingCart);
    }
    @Override
    @Transactional
    public void deleteAll(long userId){
        shoppingCartRepository.deleteByUsersId(userId);
    }


}
