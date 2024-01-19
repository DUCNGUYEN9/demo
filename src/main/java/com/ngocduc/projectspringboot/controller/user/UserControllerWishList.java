package com.ngocduc.projectspringboot.controller.user;

import com.ngocduc.projectspringboot.model.dto.request.WishListRequest;
import com.ngocduc.projectspringboot.model.dto.response.FileInfoResponse;
import com.ngocduc.projectspringboot.model.dto.response.WishListResponse;
import com.ngocduc.projectspringboot.model.entity.Users;
import com.ngocduc.projectspringboot.repository.ProductRepository;
import com.ngocduc.projectspringboot.security.CustomUserDetail;
import com.ngocduc.projectspringboot.service.FileUploadService;
import com.ngocduc.projectspringboot.service.ProductService;
import com.ngocduc.projectspringboot.service.UsersService;
import com.ngocduc.projectspringboot.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.util.*;

@RestController
public class UserControllerWishList {

    @Autowired
    private WishListService wishListService;
    @Autowired
    private ProductRepository productRepository;


    @PostMapping("/api/v1/wish-list")
    public ResponseEntity<?> addProductInWishList(@RequestParam long productId) {
        long userId = returnUserIdAuthentication();
        boolean isExist = wishListService.isExist(productId,userId);
        if (!isExist){
        return ResponseEntity.ok(wishListService.addNew(userId,productRepository.findById(productId).get()));
        }else {
            return ResponseEntity.ok(false);
        }
    }
    @GetMapping("/api/v1/wish-list")
    public ResponseEntity<?> getAllWishList(){
        long userId = returnUserIdAuthentication();
        return ResponseEntity.ok(wishListService.getAllWishList(userId));
    }

    @DeleteMapping("/api/v1/wish-list/{wishListId}")
    public ResponseEntity<?> deleteProductWishListId(@PathVariable long wishListId) {
        long userId = returnUserIdAuthentication();
        boolean result = wishListService.deleteWishList(wishListId,userId);

        return ResponseEntity.ok(result);
    }
    //get userId logging

    public static long returnUserIdAuthentication(){
        return ((CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
