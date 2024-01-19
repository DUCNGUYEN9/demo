package com.ngocduc.projectspringboot.controller.admin;

import com.ngocduc.projectspringboot.model.dto.request.ProductRequest;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminControllerProduct {
    @Autowired
    private ProductService productService;


    @GetMapping("/api/v1/admin/products")
    public ResponseEntity<Map<String, Object>> getAllProductPagSort(
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

    @GetMapping("/api/v1/admin/products/{productId}")
    public ResponseEntity<?> getInfoProductById(@PathVariable long productId) {
        return ResponseEntity.ok(productService.getInfoProductById(productId));
    }

    @PostMapping("/api/v1/admin/products")
    public ResponseEntity<?> AddNewProducts(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.create(productRequest);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/api/v1/admin/products/{productId}")
    public ResponseEntity<?> updateInfoProduct(@PathVariable long productId,@RequestBody ProductRequest productRequest) {
        boolean result = productService.findProductId(productId);
        if (result){
        ProductResponse productResponse = productService.update(productRequest,productId);
        return ResponseEntity.ok(productResponse);
        }else {
            return ResponseEntity.ok("Not found "+productId);
        }
    }

    @DeleteMapping("/api/v1/admin/products/{productId}")
    public ResponseEntity<?> deleteProducts(@PathVariable long productId) {
        return ResponseEntity.ok(productService.delete(productId));
    }


}
