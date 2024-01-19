package com.ngocduc.projectspringboot.repository;

import com.ngocduc.projectspringboot.model.entity.EOrders;
import com.ngocduc.projectspringboot.model.entity.OrderDetail;
import com.ngocduc.projectspringboot.model.entity.Orders;
import com.ngocduc.projectspringboot.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    @Query("SELECT o FROM Orders o WHERE (:status IS NULL OR o.order_status = :status)")
    List<Orders> findByOrder_status(@Param("status") EOrders status);
    List<Orders> findAllByUsers(Users users);
    @Query("SELECT o FROM Orders o WHERE o.users.id = :users and o.order_id = :orderId")

    Orders findByUsersAndOrder_id(long users,long orderId);
    @Query("SELECT sum(o.total_price) FROM Orders o WHERE (o.order_status in (:confirm,:del,:success)) and (o.create_at between :from and :to)")

    BigDecimal getByCreate_at(Date from,Date to,EOrders confirm,EOrders del,EOrders success);


}
