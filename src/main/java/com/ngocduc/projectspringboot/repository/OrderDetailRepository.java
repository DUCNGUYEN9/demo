package com.ngocduc.projectspringboot.repository;

import com.ngocduc.projectspringboot.model.entity.OrderDetail;
import com.ngocduc.projectspringboot.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findOrderDetailByOrder(Orders orders);

}
