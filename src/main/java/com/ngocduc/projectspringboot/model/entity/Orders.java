package com.ngocduc.projectspringboot.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long order_id;
    @Column(nullable = false,unique = true,columnDefinition = "varchar(100)")
    private String serial_number;
    private LocalDateTime order_at;
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal total_price;
    @Enumerated(EnumType.STRING)
    private EOrders order_status;
    @Column(columnDefinition = "varchar(100)")
    private String note;
    @Column(columnDefinition = "varchar(100)")
    private String receive_name;
    @Column(columnDefinition = "varchar(255)")
    private String receive_address;
    @Column(columnDefinition = "varchar(15)")
    private String receive_phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date create_at;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updated_at;
    @ManyToOne
    @JoinColumn(name = "user_id"
            ,referencedColumnName = "user_id")
    private Users users;

    @OneToMany(mappedBy = "order"
            ,cascade = CascadeType.ALL
            ,orphanRemoval = true)
    private List<OrderDetail> orderDetails;



}
