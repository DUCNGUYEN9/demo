package com.ngocduc.projectspringboot.controller.permitAll;

import com.ngocduc.projectspringboot.model.dto.request.LoginRequest;
import com.ngocduc.projectspringboot.model.dto.request.UserRequest;
import com.ngocduc.projectspringboot.model.dto.response.LoginResponse;
import com.ngocduc.projectspringboot.model.dto.response.UserResponse;
import com.ngocduc.projectspringboot.model.entity.Category;
import com.ngocduc.projectspringboot.repository.ProductRepository;
import com.ngocduc.projectspringboot.service.CategoryService;
import com.ngocduc.projectspringboot.service.ProductService;
import com.ngocduc.projectspringboot.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PermitAllController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;


    @PostMapping("/api/v1/auth/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        boolean isExistUserName = usersService.existsByUserName(userRequest.getUserName());
        if (isExistUserName) {
            return ResponseEntity.badRequest().body("UserName is exist");
        }
        boolean isExistEmail = usersService.existsByEmail(userRequest.getEmail());
        if (isExistEmail) {
            return ResponseEntity.badRequest().body("Email is exist");
        }
        UserResponse registerResponse = usersService.saveOrUpdate(userRequest);
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @PostMapping("/api/v1/auth/sign-in")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = usersService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/api/v1/catalog")
    public ResponseEntity<?> getAllCatalog() {
        List<Category> categoryList = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryList);
    }


}
