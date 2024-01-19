package com.ngocduc.projectspringboot.model.dto.request;

import com.ngocduc.projectspringboot.model.entity.EOrders;
import com.ngocduc.projectspringboot.model.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private List<Long> productId;
    private Long userId;
    private String serial_number;
    private LocalDateTime order_at;
    private BigDecimal total_price;
    private EOrders order_status;
    private String note;
    private String receive_name;
    private String receive_address;
    private String receive_phone;
    private Date create_at;

}
