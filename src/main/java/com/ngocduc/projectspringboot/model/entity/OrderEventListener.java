package com.ngocduc.projectspringboot.model.entity;

import com.ngocduc.projectspringboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderEventListener {

    @Autowired
    private ProductService productService;

    @EventListener
    public void handleOrderEvent(OrderEvent orderEvent) {
        Orders order = orderEvent.getOrder();
        List<OrderDetail> orderItems = order.getOrderDetails();

        for (OrderDetail orderItem : orderItems) {
            Products product = orderItem.getProducts();
            int quantity = orderItem.getOrder_quantity();

            // Cập nhật số lượng sản phẩm
            productService.updateProductQuantity(product.getProduct_id(), quantity);
        }
    }
}

