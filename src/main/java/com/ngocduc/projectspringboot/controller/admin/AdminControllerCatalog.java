package com.ngocduc.projectspringboot.controller.admin;

import com.ngocduc.projectspringboot.model.dto.request.CatalogRequest;
import com.ngocduc.projectspringboot.model.dto.request.ProductRequest;
import com.ngocduc.projectspringboot.model.dto.response.CatalogResponse;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.service.CategoryService;
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
public class AdminControllerCatalog {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;



    @GetMapping("/api/v1/admin/catalog")
    public ResponseEntity<Map<String, Object>> getAllCatalogPagSort(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable;
        if ("asc".equals(direction)) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "categoryName"));
        } else {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "categoryName"));
        }
        Page<CatalogResponse> pageCatalog = categoryService.findAll(pageable);
        Map<String, Object> data = new HashMap<>();

        data.put("catalog", pageCatalog.getContent());
        data.put("totalCatalog", pageCatalog.getTotalElements());
        data.put("totalPage", pageCatalog.getTotalPages());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/api/v1/admin/catalog/{catalogId}")
    public ResponseEntity<?> getInfoCatalogById(@PathVariable long catalogId) {
        return ResponseEntity.ok(categoryService.getInfoCatalogById(catalogId));
    }


    @PostMapping("/api/v1/admin/catalog")
    public ResponseEntity<?> addNewCatalog(@RequestBody CatalogRequest catalogRequest) {
        CatalogResponse catalogResponse = categoryService.create(catalogRequest);
        return ResponseEntity.ok(catalogResponse);
    }


    @PutMapping("/api/v1/admin/catalog/{catalogId}")
    public ResponseEntity<?> updateInfoCatalog(@PathVariable long catalogId,@RequestBody CatalogRequest catalogRequest) {
        boolean result = categoryService.findCatalogId(catalogId);
        if (result){
            CatalogResponse catalogResponse = categoryService.update(catalogRequest,catalogId);
            return ResponseEntity.ok(catalogResponse);
        }else {
            return ResponseEntity.ok("Not found "+catalogId);
        }
    }


    @DeleteMapping("/api/v1/admin/catalog/{catalogId}")
    public ResponseEntity<?> deleteCatalog(@PathVariable long catalogId) {
        return ResponseEntity.ok(categoryService.delete(catalogId));
    }


}
