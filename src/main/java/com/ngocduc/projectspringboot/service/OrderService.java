package com.ngocduc.projectspringboot.service;

import com.ngocduc.projectspringboot.model.dto.request.OrderDetailRequest;
import com.ngocduc.projectspringboot.model.dto.response.OrderDetailResponse;
import com.ngocduc.projectspringboot.model.dto.response.OrderResponse;
import com.ngocduc.projectspringboot.model.entity.Orders;
import com.ngocduc.projectspringboot.model.entity.Users;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {
    Orders createOrder(Users user, List<OrderDetailRequest> orderDetailRequestList);
    List<OrderResponse> findAll();
    List<OrderResponse> findByOrderStatus(String status);
    List<OrderDetailResponse> getDetailOrderByOrderId(long orderId);
    boolean updateOrderStatus(long orderId,String status);
    BigDecimal dashBoardSaleByTime(Date from,Date to);
}
