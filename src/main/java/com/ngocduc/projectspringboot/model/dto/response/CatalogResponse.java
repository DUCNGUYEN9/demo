package com.ngocduc.projectspringboot.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogResponse {
    private long catalogId;
    private String categoryName;
    private String description;
    private boolean category_status;
}
