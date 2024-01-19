package com.ngocduc.projectspringboot.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ngocduc.projectspringboot.token.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    @Column(name = "user_name",unique = true, nullable = false,columnDefinition = "varchar(100)")
    private String userName;
    @Column(name = "password",nullable = false,columnDefinition = "varchar(255)")
    private String password;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "full_name", nullable = false,columnDefinition = "varchar(100)")
    private String fullName;
    @Column(name = "avatar",columnDefinition = "varchar(255)")
    private String avatar;
    @Column(columnDefinition = "varchar(15)",unique = true)
    private String phone;
    @Column(columnDefinition = "varchar(255)",nullable = false)
    private String address;
    @Column(name = "user_status")
    private boolean status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date create_at;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updated_at;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Roles"
            ,joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> listRoles = new HashSet<>();
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Token> tokens;



    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Orders> orders;

    @OneToMany(mappedBy = "users")
    private List<ShoppingCart> shoppingCart;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Address> addresses;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<WishList> wishLists;

}