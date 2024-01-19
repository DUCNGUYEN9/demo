package com.ngocduc.projectspringboot.mapper;

import com.ngocduc.projectspringboot.model.dto.request.OrderDetailRequest;
import com.ngocduc.projectspringboot.model.dto.response.OrderDetailResponse;

import com.ngocduc.projectspringboot.model.entity.OrderDetail;

import org.springframework.stereotype.Component;

@Component
public class MapperOrderDetail implements  MapperGeneric<OrderDetail, OrderDetailRequest, OrderDetailResponse>{

    @Override
    public OrderDetail mapperRequestToEntity(OrderDetailRequest orderDetailRequest) {
        return null;
    }

    @Override
    public OrderDetailResponse mapperEntityToResponse(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .productId(orderDetail.getProducts().getProduct_id())
                .productName(orderDetail.getName())
                .price(orderDetail.getUnit_price())
                .quantity(orderDetail.getOrder_quantity())
                .build();
    }
}
