package com.ngocduc.projectspringboot.repository;

import com.ngocduc.projectspringboot.model.entity.Category;
import com.ngocduc.projectspringboot.model.entity.OrderDetail;
import com.ngocduc.projectspringboot.model.entity.Products;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
    @Query("select p from Products p where p.productName like %:name% or p.description like %:name%")
    List<Products> searchProductsByName(String name);
    @Query("select p from Products p where p.product_id = :productId and p.stock_quantity >= :stockQuantity")
    Products checkQuantity(long productId,int stockQuantity);

    // Lấy danh sách sản phẩm mới trong khoảng 2 tuần
    @Query("SELECT p FROM Products p WHERE p.create_at >= :startDate AND p.create_at <= :endDate")
    List<Products> findNewProductsInLastTwoWeeks(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // Lấy danh sách sản phẩm bán chạy dựa trên số lượng bán
    @Query("SELECT od.products FROM OrderDetail od GROUP BY od.products ORDER BY SUM(od.order_quantity) DESC")
    List<Products> findBestSellingProducts(Pageable pageable);

    List<Products> findByCategoryIs(Category category);
    @Query("SELECT od.products, SUM(od.order_quantity) as totalQuantity " +
            "FROM OrderDetail od " +
            "JOIN od.order o " +
            "WHERE MONTH(o.create_at) = MONTH(CURRENT_DATE) AND YEAR(o.create_at) = YEAR(CURRENT_DATE) " +
            "GROUP BY od.products " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findBestSellingProducts();
    @Query("SELECT c.categoryName, SUM(od.unit_price) as totalQuantity " +
            "FROM OrderDetail od " +
            "JOIN od.products p " +
            "JOIN p.category c " +
            "GROUP BY c.categoryName " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findSaleCategory();


}
