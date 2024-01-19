package com.ngocduc.projectspringboot.repository;

import com.ngocduc.projectspringboot.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select c from Category c where c.category_status = true ")
    List<Category> findAllByCategoryStatus();
}
