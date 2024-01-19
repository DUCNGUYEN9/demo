package com.ngocduc.projectspringboot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long category_id;
    @Column(name = "category_name",nullable = false,unique = true,columnDefinition = "varchar(100)")
    private String categoryName;
    @Column(columnDefinition = "text")
    private String description;
    private boolean category_status;
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Products> products;

    public Category(long category_id) {
        this.category_id = category_id;
    }
}
