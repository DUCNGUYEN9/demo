package com.ngocduc.projectspringboot.controller.user;

import com.ngocduc.projectspringboot.model.dto.request.OrderDetailRequest;
import com.ngocduc.projectspringboot.model.dto.request.OrderRequest;
import com.ngocduc.projectspringboot.model.dto.request.ShoppingCartRequest;
import com.ngocduc.projectspringboot.model.dto.response.OrderResponse;
import com.ngocduc.projectspringboot.model.entity.Orders;
import com.ngocduc.projectspringboot.model.entity.Users;
import com.ngocduc.projectspringboot.repository.UsersRepository;
import com.ngocduc.projectspringboot.security.CustomUserDetail;
import com.ngocduc.projectspringboot.service.OrderService;
import com.ngocduc.projectspringboot.service.ShoppingCartService;
import com.ngocduc.projectspringboot.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserControllerShoppingCart {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;


//    @PostMapping("/api/v1/shopping-cart")
//    public ResponseEntity<?> createShoppingCart() {
//        long userId = returnUserIdAuthentication();
//        return ResponseEntity.ok(wishListService.addNew(userId,productRepository.findById(productId).get()));
//        return ResponseEntity.ok("createShoppingCart");
//    }

    @PostMapping("/api/v1/shopping-cart/{userId}/add")
    public ResponseEntity<?> addNewProductInCart(@PathVariable long userId, @RequestBody ShoppingCartRequest shoppingCartRequest) {
        boolean isExistProduct = shoppingCartService.isExist( userId,shoppingCartRequest);
        if (isExistProduct) {
            return ResponseEntity.ok(shoppingCartRequest.getProductId() + " is existed !");
        }
        boolean checkQuantity = shoppingCartService.checkQuantity(shoppingCartRequest.getProductId(), shoppingCartRequest.getQuantity());
        if (checkQuantity) {
            return ResponseEntity.ok(shoppingCartService.addToCart(userId, shoppingCartRequest));
        } else {
            return ResponseEntity.ok("quantity is not enough !");

        }
    }

    @GetMapping("/api/v1/shopping-cart/{userId}")
    public ResponseEntity<?> listProductInCart(@PathVariable long userId) {
       Users users = usersRepository.findById(userId).get();
        return ResponseEntity.ok(shoppingCartService.findAll(users));
    }


    @PutMapping("/api/v1/shopping-cart/{userId}/update/{cartItemId}")
    public ResponseEntity<?> updateQuantity(@RequestParam int quantity, @PathVariable long userId, @PathVariable long cartItemId) {
        boolean checkQuantity = shoppingCartService.checkQuantity(cartItemId, quantity);
        if (checkQuantity) {
            return ResponseEntity.ok(shoppingCartService.updateQuantity(cartItemId,quantity,userId));
        } else {
            return ResponseEntity.ok("quantity is not enough !");
        }
    }


    @DeleteMapping("/api/v1/shopping-cart/{userId}/delete/{shoppingCartId}")
    public ResponseEntity<?> deleteAProductInCart(@PathVariable long userId, @PathVariable long shoppingCartId) {
        shoppingCartService.delete(shoppingCartId);
        return ResponseEntity.ok("deleteAProductinCart");
    }

    @DeleteMapping("/api/v1/shopping-cart/{userId}/clear")
    public ResponseEntity<?> deleteAllProductInCart(@PathVariable long userId) {
        shoppingCartService.deleteAll(userId);

        return ResponseEntity.ok("deleteAllProductInCart");
    }

    @PostMapping("/api/v1/shopping-cart/{userId}/check-out")
    public ResponseEntity<?> checkOut(@PathVariable long userId,
                                      @RequestBody List<OrderDetailRequest> orderDetailRequestList) {
        Users users = usersRepository.findById(userId).get();
        Orders orders = orderService.createOrder(users,orderDetailRequestList);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(orders.getOrder_id());
        orderResponse.setOrderStatus(orders.getOrder_status());
        orderResponse.setTotalAmount(orders.getTotal_price());
        orderResponse.setMessage("Order success !");

        return ResponseEntity.ok(orderResponse);
    }


    public static long returnUserIdAuthentication() {
        return ((CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
