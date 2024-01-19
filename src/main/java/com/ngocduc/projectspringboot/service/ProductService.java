package com.ngocduc.projectspringboot.service;

import com.ngocduc.projectspringboot.model.dto.request.ProductRequest;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.model.dto.response.UserResponse;
import com.ngocduc.projectspringboot.model.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ProductService {
    List<Products> sortByName(String direction);
    List<Products> sortByPriceAndName(String priceDirection,String nameDirection);
    Page<ProductResponse> findAll(Pageable pageable);
    ProductResponse create(ProductRequest productRequest);
    ProductResponse update(ProductRequest productRequest,long productId);
    ProductResponse getInfoProductById(long productId);
    boolean findProductId(long productId);
    ProductResponse delete(long productId);
    List<ProductResponse> searchProductByName(String name);

    void updateProductQuantity(long productId,int quantity);
   List<ProductResponse> findNewProductsInLastTwoWeeks(Date startDate, Date endDate);
    List<ProductResponse> findBestSellingProducts(Pageable pageable);
    List<ProductResponse> findListProductByCatalogId(long catalogId);

//    List<ProductResponse> findByProductsBestSellerInMonth();
}
