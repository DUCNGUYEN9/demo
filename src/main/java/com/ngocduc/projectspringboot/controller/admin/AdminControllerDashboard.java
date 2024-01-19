package com.ngocduc.projectspringboot.controller.admin;

import com.ngocduc.projectspringboot.model.dto.request.ProductRequest;
import com.ngocduc.projectspringboot.model.dto.request.SaleReq;
import com.ngocduc.projectspringboot.model.dto.response.DashboardSaleByCatalogResponse;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.repository.ProductRepository;
import com.ngocduc.projectspringboot.service.OrderService;
import com.ngocduc.projectspringboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class AdminControllerDashboard {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/api/v1/admin/dash-board/sales")
    public ResponseEntity<?> dashBoardSaleByTime(@RequestBody SaleReq saleReq) {

        return ResponseEntity.ok(orderService.dashBoardSaleByTime(saleReq.getFrom(),saleReq.getTo()));
    }

    @GetMapping("/api/v1/admin/dash-board/sales/best-seller-products")
    public ResponseEntity<?> bestSellerProductsByMonth() {

        return ResponseEntity.ok(productRepository.findBestSellingProducts());
    }

    @GetMapping("/api/v1/admin/dash-board/sales/catalog")
    public ResponseEntity<?> dashBoardSaleByCatalog() {
        List<Object[]> objects =productRepository.findSaleCategory();
        List<DashboardSaleByCatalogResponse> responseList = new ArrayList<>();

        for (Object[] obj : objects) {
            String categoryName = (String) obj[0];
            BigDecimal totalQuantity = (BigDecimal) obj[1];

            DashboardSaleByCatalogResponse response = new DashboardSaleByCatalogResponse(categoryName, totalQuantity);
            responseList.add(response);
        }
        return ResponseEntity.ok(responseList);
    }
}
