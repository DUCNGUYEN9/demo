package com.ngocduc.projectspringboot.service.serviceImp;

import com.ngocduc.projectspringboot.mapper.MapperProducts;
import com.ngocduc.projectspringboot.model.dto.request.ProductRequest;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.model.entity.Category;
import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.repository.CategoryRepository;
import com.ngocduc.projectspringboot.repository.ProductRepository;
import com.ngocduc.projectspringboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MapperProducts mapperProducts;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Products> sortByName(String direction) {

        if (direction.equals("asc")) {
            return productRepository.findAll(Sort.by("productName").ascending());
        }
        return productRepository.findAll(Sort.by("productName").descending());
    }

    @Override
    public List<Products> sortByPriceAndName(String priceDirection, String nameDirection) {
        return null;
    }

    @Override
    public Page<ProductResponse> findAll(Pageable pageable) {
        Page<Products> productEntityPage = productRepository.findAll(pageable);

        List<Products> productsList = productEntityPage.getContent();

        List<ProductResponse> productResponseList = productsList.stream()
                .map(products -> mapperProducts.mapperEntityToResponse(products))
                .collect(Collectors.toList());

        var totalProduct = productEntityPage.getTotalElements();

        return new PageImpl<>(productResponseList, pageable, totalProduct);
    }


    @Override
    public ProductResponse create(ProductRequest productRequest) {
        return mapperProducts.mapperEntityToResponse(productRepository.save(mapperProducts
                .mapperRequestToEntity(productRequest)));
    }

    @Override
    public ProductResponse update(ProductRequest productRequest, long productId) {
        Optional<Products> productsOptional = productRepository.findById(productId);
        if (productsOptional.isPresent()) {
            Products products = productsOptional.get();
            products.setProductName(productRequest.getProduct_name());
            products.setUpdated_at(new Date());
            products.setDescription(productRequest.getDescription());
            products.setImage(productRequest.getImage());
            products.setCategory(new Category(productRequest.getCategory()));
            return mapperProducts.mapperEntityToResponse(productRepository.save(products));
        } else {
            return null;
        }
    }

    @Override
    public ProductResponse getInfoProductById(long productId) {
        Optional<Products> productsOptional = productRepository.findById(productId);
        return productsOptional.map(products ->
                mapperProducts.mapperEntityToResponse(products)).orElse(null);
    }

    @Override
    public boolean findProductId(long productId) {
        Optional<Products> productsOptional = productRepository.findById(productId);
        return productsOptional.isPresent();
    }

    @Override
    public ProductResponse delete(long productId) {
        Optional<Products> productsOptional = productRepository.findById(productId);
        if (productsOptional.isPresent()) {
            productRepository.deleteById(productId);
            return mapperProducts.mapperEntityToResponse(productsOptional.get());
        } else {
            return null;
        }
    }

    @Override
    public List<ProductResponse> searchProductByName(String name) {
        List<ProductResponse> productResponseList = new ArrayList<>();
        List<Products> productsList = productRepository.searchProductsByName(name);

        if (productsList != null && !productsList.isEmpty()) {
            productResponseList = productsList.stream()
                    .map(products -> mapperProducts.mapperEntityToResponse(products))
                    .collect(Collectors.toList());
        }

        return productResponseList;
    }

    @Override
    public void updateProductQuantity(long productId, int quantity) {
        Products products = productRepository.findById(productId).get();
        products.setStock_quantity(products.getStock_quantity() - quantity);
        productRepository.save(products);
    }

    @Override
    public List<ProductResponse> findNewProductsInLastTwoWeeks(Date startDate, Date endDate) {
        List<Products> productsList = productRepository.findNewProductsInLastTwoWeeks(startDate, endDate);
        List<ProductResponse> productResponseList = productsList.stream()
                .map(products -> mapperProducts.mapperEntityToResponse(products))
                .collect(Collectors.toList());
        return productResponseList;
    }

    @Override
    public List<ProductResponse> findBestSellingProducts(Pageable pageable) {
        List<Products> productsList = productRepository.findBestSellingProducts(pageable);
        List<ProductResponse> productResponseList = productsList.stream()
                .map(products -> mapperProducts.mapperEntityToResponse(products))
                .collect(Collectors.toList());
        return productResponseList;
    }

    @Override
    public List<ProductResponse> findListProductByCatalogId(long catalogId) {
        Category category = categoryRepository.findById(catalogId).get();

        List<Products> productsList = productRepository.findByCategoryIs(category);
        List<ProductResponse> productResponseList = productsList.stream()
                .map(products -> mapperProducts.mapperEntityToResponse(products))
                .collect(Collectors.toList());
        return productResponseList;
    }
//    @Override
//    public List<ProductResponse> findByProductsBestSellerInMonth() {
//        List<Products> productsList = productRepository.findByProductsBestSellerInMonth();
//        List<ProductResponse> productResponseList = productsList.stream()
//                .map(products -> mapperProducts.mapperEntityToResponse(products))
//                .collect(Collectors.toList());
//        return productResponseList;
//    }
}
