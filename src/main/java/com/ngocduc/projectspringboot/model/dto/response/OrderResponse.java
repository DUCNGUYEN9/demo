package com.ngocduc.projectspringboot.model.dto.response;

import com.ngocduc.projectspringboot.model.entity.EOrders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sound.sampled.Line;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private BigDecimal totalAmount;
    private EOrders orderStatus;
    private String message;

}

