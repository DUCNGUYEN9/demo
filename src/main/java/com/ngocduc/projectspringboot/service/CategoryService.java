package com.ngocduc.projectspringboot.service;

import com.ngocduc.projectspringboot.model.dto.request.CatalogRequest;
import com.ngocduc.projectspringboot.model.dto.request.ProductRequest;
import com.ngocduc.projectspringboot.model.dto.response.CatalogResponse;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Page<CatalogResponse> findAll(Pageable pageable);

    CatalogResponse create(CatalogRequest catalogRequest);
    CatalogResponse getInfoCatalogById(long catalogId);
    boolean findCatalogId(long catalogId);
    CatalogResponse update(CatalogRequest catalogRequest, long catalogId);
    CatalogResponse delete(long catalogId);


}
