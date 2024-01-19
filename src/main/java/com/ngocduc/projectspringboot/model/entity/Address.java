package com.ngocduc.projectspringboot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long address_id;
    @ManyToOne
    @JoinColumn(name = "user_id"
            ,referencedColumnName = "user_id")
    private Users users;
    @Column(columnDefinition = "varchar(255)")
    private String full_address;
    @Column(columnDefinition = "varchar(15)")
    private String phone;
    @Column(columnDefinition = "varchar(50)")
    private String receive_name;
}
