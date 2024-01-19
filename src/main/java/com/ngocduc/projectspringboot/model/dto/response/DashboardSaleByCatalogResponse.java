package com.ngocduc.projectspringboot.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSaleByCatalogResponse {
    private String categoryName;
    private BigDecimal totalQuantity;
}
