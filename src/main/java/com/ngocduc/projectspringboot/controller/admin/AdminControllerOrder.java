package com.ngocduc.projectspringboot.controller.admin;

import com.ngocduc.projectspringboot.model.dto.request.ProductRequest;
import com.ngocduc.projectspringboot.model.dto.request.TextReq;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.service.OrderService;
import com.ngocduc.projectspringboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class AdminControllerOrder {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;


    @GetMapping("/api/v1/admin/orders")
    public ResponseEntity<?> getListAllOrders() {

        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/api/v1/admin/orders/{orderStatus}")
    public ResponseEntity<?> getListOrderStatus(@PathVariable String orderStatus) {

        return ResponseEntity.ok(orderService.findByOrderStatus(orderStatus));
    }

    @GetMapping("/api/v1/admin/orders/orderId/{orderId}")
    public ResponseEntity<?> detailOrders(@PathVariable long orderId) {

        return ResponseEntity.ok(orderService.getDetailOrderByOrderId(orderId));
    }

    @PutMapping("/api/v1/admin/orders/{orderId}/status")
    public ResponseEntity<?> updateStatusOrders(@PathVariable long orderId,@RequestBody TextReq status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId,status.getStatus()));
    }


}
