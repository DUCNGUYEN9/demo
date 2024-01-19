package com.ngocduc.projectspringboot.mapper;

import com.ngocduc.projectspringboot.model.dto.request.OrderRequest;
import com.ngocduc.projectspringboot.model.dto.response.OrderResponse;
import com.ngocduc.projectspringboot.model.entity.Orders;
import org.springframework.stereotype.Component;

@Component
public class MapperOrder implements  MapperGeneric<Orders, OrderRequest, OrderResponse>{

    @Override
    public Orders mapperRequestToEntity(OrderRequest orderRequest) {
        return null;
    }

    @Override
    public OrderResponse mapperEntityToResponse(Orders orders) {
        return OrderResponse.builder()
                .orderId(orders.getOrder_id())
                .orderStatus(orders.getOrder_status())
                .message(orders.getNote())
                .totalAmount(orders.getTotal_price())
                .build();
    }
}
