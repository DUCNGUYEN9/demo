package com.ngocduc.projectspringboot.model.dto.response;

import com.ngocduc.projectspringboot.model.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private Set<Roles> listRoles;

}
