package com.ngocduc.projectspringboot.service.serviceImp;

import com.ngocduc.projectspringboot.mapper.MapperOrder;
import com.ngocduc.projectspringboot.mapper.MapperOrderDetail;
import com.ngocduc.projectspringboot.model.dto.request.OrderDetailRequest;
import com.ngocduc.projectspringboot.model.dto.response.OrderDetailResponse;
import com.ngocduc.projectspringboot.model.dto.response.OrderResponse;
import com.ngocduc.projectspringboot.model.entity.*;
import com.ngocduc.projectspringboot.repository.OrderDetailRepository;
import com.ngocduc.projectspringboot.repository.OrderRepository;
import com.ngocduc.projectspringboot.repository.ProductRepository;
import com.ngocduc.projectspringboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MapperOrder mapperOrder;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private MapperOrderDetail mapperOrderDetail;

    @Transactional
    @Override
    public Orders createOrder(Users user, List<OrderDetailRequest> orderDetailRequestList) {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        Orders order = new Orders();
        order.setUsers(user);
        order.setCreate_at(new Date());
        order.setOrder_at(LocalDateTime.now());
        order.setOrder_status(EOrders.WAITING);
        order.setSerial_number(Products.generateUniqueCode());
        order.setNote("nha");

        for (OrderDetailRequest odr : orderDetailRequestList) {
            OrderDetail orderDetail = new OrderDetail();
            Products product = productRepository.findById(odr.getProductId()).orElse(null);
            if (product != null) {
                orderDetail.setProducts(product);
                orderDetail.setName(product.getProductName());
                orderDetail.setOrder_quantity(odr.getQuantity());
                orderDetail.setUnit_price(odr.getPrice());
                orderDetail.setOrder(order); // Thiết lập quan hệ với Order

                orderDetailList.add(orderDetail);
                total = total.add(orderDetail.getUnit_price().multiply(BigDecimal.valueOf(orderDetail.getOrder_quantity())));
            }
        }

        order.setTotal_price(total);
        order.setOrderDetails(orderDetailList);

        // Lưu đơn hàng vào cơ sở dữ liệu
        orderRepository.save(order);

        // Phát ra sự kiện OrderEvent
        eventPublisher.publishEvent(new OrderEvent(this, order));

        return order;
    }

    @Override
    public List<OrderResponse> findAll() {
        List<Orders> ordersList = orderRepository.findAll();
        List<OrderResponse> orderResponseList = ordersList.stream()
                .map(orders -> mapperOrder.mapperEntityToResponse(orders))
                .collect(Collectors.toList());
        return orderResponseList;
    }

    @Override
    public List<OrderResponse> findByOrderStatus(String status) {
        List<Orders> ordersList = new ArrayList<>();
        if (status.equals("WAITING")) {
            ordersList = orderRepository.findByOrder_status(EOrders.WAITING);
        }
        if (status.equals("CONFIRM")) {
            ordersList = orderRepository.findByOrder_status(EOrders.CONFIRM);
        }
        if (status.equals("DELIVERY")) {
            ordersList = orderRepository.findByOrder_status(EOrders.DELIVERY);
        }
        if (status.equals("SUCCESS")) {
            ordersList = orderRepository.findByOrder_status(EOrders.SUCCESS);
        }
        if (status.equals("CANCEL")) {
            ordersList = orderRepository.findByOrder_status(EOrders.CANCEL);
        }
        if (status.equals("DENIED")) {
            ordersList = orderRepository.findByOrder_status(EOrders.DENIED);
        }
        List<OrderResponse> orderResponseList = ordersList.stream()
                .map(orders -> mapperOrder.mapperEntityToResponse(orders))
                .collect(Collectors.toList());
        return orderResponseList;
    }

    @Override
    public List<OrderDetailResponse> getDetailOrderByOrderId(long orderId) {
        Orders orders = orderRepository.findById(orderId).get();
        List<OrderDetail> orderDetailList = orderDetailRepository.findOrderDetailByOrder(orders);
        List<OrderDetailResponse> odr = orderDetailList.stream()
                .map(detail -> mapperOrderDetail.mapperEntityToResponse(detail))
                .collect(Collectors.toList());
        return odr;
    }

    @Override
    public boolean updateOrderStatus(long orderId, String status) {


        Optional<Orders> orders = orderRepository.findById(orderId);
        if (orders.isPresent()) {
            Orders ordersStatus = orders.get();
            switch (status) {
                case "CONFIRM" -> ordersStatus.setOrder_status(EOrders.valueOf("CONFIRM"));
                case "DELIVERY" -> ordersStatus.setOrder_status(EOrders.valueOf("DELIVERY"));
                case "SUCCESS" -> ordersStatus.setOrder_status(EOrders.valueOf("SUCCESS"));
                case "CANCEL" -> ordersStatus.setOrder_status(EOrders.valueOf("CANCEL"));
                default -> ordersStatus.setOrder_status(EOrders.valueOf("DENIED"));
            }
            orderRepository.save(ordersStatus);
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal dashBoardSaleByTime(Date from, Date to) {

        return orderRepository.getByCreate_at(from,to,EOrders.CONFIRM,EOrders.DELIVERY,EOrders.SUCCESS);
    }

}
