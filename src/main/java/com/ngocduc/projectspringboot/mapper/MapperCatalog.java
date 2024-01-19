package com.ngocduc.projectspringboot.mapper;

import com.ngocduc.projectspringboot.model.dto.request.CatalogRequest;
import com.ngocduc.projectspringboot.model.dto.request.ProductRequest;
import com.ngocduc.projectspringboot.model.dto.response.CatalogResponse;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.model.entity.Category;
import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MapperCatalog implements  MapperGeneric<Category, CatalogRequest, CatalogResponse>{

    @Override
    public Category mapperRequestToEntity(CatalogRequest catalogRequest) {

        return Category.builder().categoryName(catalogRequest.getCategoryName())
                .description(catalogRequest.getDescription())
                .category_status(catalogRequest.isCategory_status())
                .build();
    }

    @Override
    public CatalogResponse mapperEntityToResponse(Category category) {
        return CatalogResponse.builder()
                .catalogId(category.getCategory_id())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .category_status(category.isCategory_status())
                .build();
    }
}
