package com.ngocduc.projectspringboot.service.serviceImp;

import com.ngocduc.projectspringboot.mapper.MapperCatalog;
import com.ngocduc.projectspringboot.model.dto.request.CatalogRequest;
import com.ngocduc.projectspringboot.model.dto.response.CatalogResponse;
import com.ngocduc.projectspringboot.model.dto.response.ProductResponse;
import com.ngocduc.projectspringboot.model.entity.Category;
import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.repository.CategoryRepository;
import com.ngocduc.projectspringboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MapperCatalog mapperCatalog;
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAllByCategoryStatus();
    }

    @Override
    public Page<CatalogResponse> findAll(Pageable pageable) {
        Page<Category> categoriesEntityPage = categoryRepository.findAll(pageable);

        List<Category> categoriesList = categoriesEntityPage.getContent();

        List<CatalogResponse> catalogResponseList = categoriesList.stream()
                .map(category -> mapperCatalog.mapperEntityToResponse(category))
                .collect(Collectors.toList());

        var totalCatalog = categoriesEntityPage.getTotalElements();

        return new PageImpl<>(catalogResponseList, pageable, totalCatalog);
    }

    @Override
    public CatalogResponse create(CatalogRequest catalogRequest) {
        return mapperCatalog.mapperEntityToResponse(categoryRepository.save(mapperCatalog
                .mapperRequestToEntity(catalogRequest)));
    }

    @Override
    public CatalogResponse getInfoCatalogById(long catalogId) {
        Optional<Category> categoryOptional = categoryRepository.findById(catalogId);
        return categoryOptional.map(category ->
                mapperCatalog.mapperEntityToResponse(category)).orElse(null);
    }

    @Override
    public boolean findCatalogId(long catalogId) {
        Optional<Category> catalogOptional = categoryRepository.findById(catalogId);
        return catalogOptional.isPresent();
    }

    @Override
    public CatalogResponse update(CatalogRequest catalogRequest, long catalogId) {
        Optional<Category> categoryOptional = categoryRepository.findById(catalogId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setCategoryName(catalogRequest.getCategoryName());
            category.setDescription(catalogRequest.getDescription());
            category.setCategory_status(catalogRequest.isCategory_status());
            return mapperCatalog.mapperEntityToResponse(categoryRepository.save(category));
        } else {
            return null;
        }
    }

    @Override
    public CatalogResponse delete(long catalogId) {
        Optional<Category> categoryOptional = categoryRepository.findById(catalogId);
        if (categoryOptional.isPresent()) {
            categoryRepository.deleteById(catalogId);
            return mapperCatalog.mapperEntityToResponse(categoryOptional.get());
        }else {
            return null;
        }
    }
}
