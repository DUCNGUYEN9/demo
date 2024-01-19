package com.ngocduc.projectspringboot.controller.permitAll;

import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.model.dto.response.UserResponse;
import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.repository.ProductRepository;
import com.ngocduc.projectspringboot.service.CategoryService;
import com.ngocduc.projectspringboot.service.ProductService;
import com.ngocduc.projectspringboot.service.UsersService;
import com.ngocduc.projectspringboot.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PermitAllControllerProduct {
    @Autowired
    private UsersService usersService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WishListService wishListService;


    @GetMapping("/api/v1/products/sortByName")
    public ResponseEntity<List<Products>> sortByName(@RequestParam(defaultValue = "asc") String direction) {
        List<Products> listProduct = productService.sortByName(direction);
        return ResponseEntity.ok(listProduct);
    }


//    public ResponseEntity<Map<String, Object>> findAllPag(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "3") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<ProductResponse> pageProduct = productService.findAll(pageable);
//        Map<String, Object> data = new HashMap<>();
//        data.put("products", pageProduct.getContent());
//        data.put("totalProduct", pageProduct.getTotalElements());
//        data.put("totalPage", pageProduct.getTotalPages());
//        return ResponseEntity.ok(data);
//    }
    @GetMapping("/api/v1/products")
    public ResponseEntity<Map<String, Object>> getAllProductsPagAndSort(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable;
        if ("asc".equals(direction)) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "productName"));
        } else {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "productName"));
        }

        Page<ProductResponse> pageProduct = productService.findAll(pageable);
        Map<String, Object> data = new HashMap<>();

        data.put("products", pageProduct.getContent());
        data.put("totalProduct", pageProduct.getTotalElements());
        data.put("totalPage", pageProduct.getTotalPages());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/api/v1/products/search")
    public ResponseEntity<?> search(@PathParam("searchName") String searchName) {
        List<ProductResponse> productResponseList = productService.searchProductByName(searchName);
        return ResponseEntity.ok(productResponseList);
    }


    @GetMapping("/api/v1/products/featured-products")
    public ResponseEntity<?> featuredProducts() {
        return ResponseEntity.ok(wishListService.getAllWishLists());
    }

    @GetMapping("/api/v1/products/new-products")
    public ResponseEntity<?> newProducts() {
        Date endDate = new Date();
        LocalDateTime startDateLocalDateTime = LocalDateTime.now().minusWeeks(2);
        Date startDate = java.sql.Timestamp.valueOf(startDateLocalDateTime);

        return ResponseEntity.ok(productService.findNewProductsInLastTwoWeeks(startDate,endDate));
    }

    @GetMapping("/api/v1/products/best-seller-products")
    public ResponseEntity<?> bestSellerProducts() {
        Pageable pageable = PageRequest.of(0,5);
        return ResponseEntity.ok(productService.findBestSellingProducts(pageable));
    }

    @GetMapping("/api/v1/products/catalog/{catalogId}")
    public ResponseEntity<?> listProductByCatalogId(@PathVariable long catalogId) {
        return ResponseEntity.ok(productService.findListProductByCatalogId(catalogId));
    }

    @GetMapping("/api/v1/products/{productId}")
    public ResponseEntity<?> productInfoById(@PathVariable long productId) {
        return ResponseEntity.ok(productService.getInfoProductById(productId));
    }


}
